package com.jdbc.student.dao;

import java.sql.*;
import java.util.*;

import com.jdbc.student.model.Student;

public class StudentDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/student";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root123";

    private static final String INSERT_STUDENT = "INSERT INTO Student (name, age, country) VALUES (?, ?, ?)";
    private static final String SELECT_STUDENT_BY_ID = "SELECT id, name, age, country FROM Student WHERE id=?";
    private static final String SELECT_ALL_STUDENTS = "SELECT * FROM Student";
    private static final String DELETE_STUDENT = "DELETE FROM Student WHERE id=?";
    private static final String UPDATE_STUDENT = "UPDATE Student SET name=?, age=?, country=? WHERE id=?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 🔹 Insert student
    public void insertStudent(String name, int age, String country) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_STUDENT)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, country);
            ps.executeUpdate();
        }
    }

    // 🔹 Select student by ID
    public Student getStudent(int id) throws SQLException {
        Student student = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_STUDENT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String country = rs.getString("country");
                student = new Student(id, name, age, country);
            }
        }
        return student;
    }

    // 🔹 Select all students
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_STUDENTS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("country")));
            }
        }
        return students;
    }

    // 🔹 Update student
    public boolean updateStudent(Student student) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_STUDENT)) {
            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getCountry());
            ps.setInt(4, student.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Delete student
    public boolean deleteStudent(int id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_STUDENT)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
