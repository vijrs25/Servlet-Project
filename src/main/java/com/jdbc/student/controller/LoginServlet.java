package com.jdbc.student.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jdbc.student.DBUtil;

@SuppressWarnings("serial")
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username"); // 👈 from React input
        String password = req.getParameter("password");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM student_signup WHERE userid=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // ✅ Session
                HttpSession session = req.getSession();
                session.setAttribute("studentUser", username);

                // ✅ Cookie
                Cookie cookie = new Cookie("lastLoginUser", username);
                cookie.setMaxAge(60 * 60 * 24); // 1 day
                resp.addCookie(cookie);

                resp.getWriter().write("{\"status\":\"SUCCESS\",\"user\":\"" + username + "\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("{\"status\":\"FAIL\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"status\":\"ERROR\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
