package com.jdbc.student.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdbc.student.DBUtil;

@SuppressWarnings("serial")
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String userid = req.getParameter("userid");
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String countrycode = req.getParameter("countrycode");
        String password = req.getParameter("password");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO student_signup (userid, name, phone, countrycode, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userid);
            ps.setString(2, name);
            ps.setString(3, phone);
            ps.setString(4, countrycode);
            ps.setString(5, password);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                out.write("{\"message\":\"Signup successful!\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"message\":\"Signup failed.\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"message\":\"Error: " + e.getMessage() + "\"}");
        }
    }
}
