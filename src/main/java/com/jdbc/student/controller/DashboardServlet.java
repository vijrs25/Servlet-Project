package com.jdbc.student.controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("studentUser") != null) {
            String user = (String) session.getAttribute("studentUser");

            // Read cookies
            String lastUser = "N/A";
            if (req.getCookies() != null) {
                for (Cookie c : req.getCookies()) {
                    if (c.getName().equals("lastLoginUser")) {
                        lastUser = c.getValue();
                    }
                }
            }

            resp.getWriter().write("{\"message\":\"Welcome " + user + "\", \"lastUser\":\"" + lastUser + "\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"Not logged in\"}");
        }
    }
}
