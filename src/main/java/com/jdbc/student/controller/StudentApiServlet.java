package com.jdbc.student.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdbc.student.dao.StudentDAO;
import com.jdbc.student.model.Student;

@SuppressWarnings("serial")
@WebServlet("/api/students")
public class StudentApiServlet extends HttpServlet {
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Student> students = studentDAO.getAllStudents();

            // Convert to JSON manually (or use Gson)
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                json.append("{")
                    .append("\"id\":").append(s.getId()).append(",")
                    .append("\"name\":\"").append(s.getName()).append("\",")
                    .append("\"age\":").append(s.getAge()).append(",")
                    .append("\"country\":\"").append(s.getCountry()).append("\"")
                    .append("}");
                if (i < students.size() - 1) json.append(",");
            }
            json.append("]");

            response.getWriter().write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
