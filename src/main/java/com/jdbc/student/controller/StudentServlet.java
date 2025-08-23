package com.jdbc.student.controller;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdbc.student.dao.StudentDAO;
import com.jdbc.student.model.Student;

@SuppressWarnings("serial")
@WebServlet("/students")  
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            List<Student> students = studentDAO.getAllStudents();
            out.println("<h2>Student List</h2>");
            for (Student s : students) {
                out.println("<p>" + s.getId() + " - " + s.getName() + " - " + s.getAge() + " - " + s.getCountry() + "</p>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
