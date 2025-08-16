package com.chat.ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat/{username}")
public class ChatEndpoint {
    private static Map<String, Session> userSessions = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        userSessions.put(username, session);
        System.out.println("✅ " + username + " connected: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String sender) throws IOException {
        // Format = "receiver:message"
        String[] parts = message.split(":", 2);
        if (parts.length == 2) {
            String receiver = parts[0];
            String text = parts[1];

            String msgJson = "{ \"sender\": \"" + sender + "\", \"receiver\": \"" + receiver + "\", \"text\": \"" + text + "\" }";

            // send to receiver
            Session receiverSession = userSessions.get(receiver);
            if (receiverSession != null && receiverSession.isOpen()) {
                receiverSession.getBasicRemote().sendText(msgJson);
            }

            // send back to sender too (for chat history)
            if (session.isOpen()) {
                session.getBasicRemote().sendText(msgJson);
            }
        }
    }


    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        userSessions.remove(username);
        System.out.println("❌ " + username + " disconnected");
    }
}
