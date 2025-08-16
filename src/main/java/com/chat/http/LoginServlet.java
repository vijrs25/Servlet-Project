package com.chat.http;

import com.google.gson.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    req.setCharacterEncoding("UTF-8");

    String body = req.getReader().lines().reduce("", (a,b)->a+b);
    JsonObject j = JsonParser.parseString(body).getAsJsonObject();
    String username = j.has("username") ? j.get("username").getAsString() : null;

    if (username == null || username.trim().isEmpty()) {
      resp.setStatus(400);
      resp.getWriter().write("{\"error\":\"username required\"}");
      return;
    }

    HttpSession session = req.getSession(true);
    session.setAttribute("username", username.trim());

    // dev CORS
    resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
    resp.setHeader("Access-Control-Allow-Credentials", "true");

    resp.getWriter().write("{\"ok\":true,\"username\":\""+username+"\"}");
  }

  @Override
  protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
    resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
    resp.setHeader("Access-Control-Allow-Credentials", "true");
    resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    resp.setHeader("Access-Control-Allow-Methods", "POST,OPTIONS");
    resp.setStatus(204);
  }
}
