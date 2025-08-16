package com.chat.ws;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.*;

public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
  @Override
  public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest req, HandshakeResponse res) {
    HttpSession http = (HttpSession) req.getHttpSession();
    if (http != null) {
      sec.getUserProperties().put(HttpSession.class.getName(), http);
    }
  }
}
