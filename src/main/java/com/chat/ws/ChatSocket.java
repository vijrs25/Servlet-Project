package com.chat.ws;

import com.google.gson.Gson;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value="/ws/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatSocket {

  private static final Map<String, Session> USER_SESSIONS = new ConcurrentHashMap<>();
  private static final Gson gson = new Gson();

  @OnOpen
  public void onOpen(Session ws, EndpointConfig cfg) throws IOException {
    HttpSession http = (HttpSession) cfg.getUserProperties().get(HttpSession.class.getName());
    if (http == null || http.getAttribute("username") == null) {
      ws.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Login required"));
      return;
    }
    String username = (String) http.getAttribute("username");

    // If already connected, close old one (simple single-session rule)
    Session old = USER_SESSIONS.put(username, ws);
    if (old != null && old.isOpen()) old.close();

    ws.getUserProperties().put("username", username);

    // greet
    ws.getBasicRemote().sendText(gson.toJson(Map.of("type","welcome","me",username)));
    broadcast(Map.of("type","presence","user",username,"online",true));
  }

  @OnMessage
  public void onMessage(Session ws, String text) throws IOException {
    // expected: {"to":"Isha","text":"hello"}
    String from = (String) ws.getUserProperties().get("username");
    @SuppressWarnings("unchecked")
    Map<String, Object> msg = gson.fromJson(text, Map.class);
    String to = (String) msg.get("to");
    String body = (String) msg.get("text");

    if (to == null || body == null) {
      ws.getBasicRemote().sendText(gson.toJson(Map.of("type","error","msg","to & text required")));
      return;
    }

    Session target = USER_SESSIONS.get(to);
    var payload = Map.of("type","message","from",from,"text",body,"ts",System.currentTimeMillis());

    // deliver to receiver
    if (target != null && target.isOpen()) {
      target.getBasicRemote().sendText(gson.toJson(payload));
    }
    // echo back/sent ack to sender
    ws.getBasicRemote().sendText(gson.toJson(Map.of("type","sent","to",to,"ts",System.currentTimeMillis())));
  }

  @OnClose
  public void onClose(Session ws, CloseReason cr) {
    String user = (String) ws.getUserProperties().get("username");
    if (user != null) {
      USER_SESSIONS.remove(user, ws);
      try { broadcast(Map.of("type","presence","user",user,"online",false)); } catch (Exception ignored) {}
    }
  }

  @OnError
  public void onError(Session ws, Throwable t) {
    // optional log
  }

  private void broadcast(Map<String, Object> data) throws IOException {
    String s = gson.toJson(data);
    for (Session sss : USER_SESSIONS.values()) {
      if (sss.isOpen()) sss.getBasicRemote().sendText(s);
    }
  }
}
