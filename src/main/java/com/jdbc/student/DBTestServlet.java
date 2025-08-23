package com.jdbc.student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@SuppressWarnings("serial")
@WebServlet("/dbtest")
public class DBTestServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection con = DBUtil.getConnection()) {
            out.println("<h2>✅ Database Connected Successfully!</h2>");
        } catch (Exception e) {
            out.println("<h2>❌ Connection Failed!</h2>");
            e.printStackTrace(out);
        }
    }
}
