
package com.campusissue.dao;

import com.campusissue.database.DatabaseConnection;
import com.campusissue.model.Student;

import java.sql.*;

public class StudentDAO {

    public boolean registerStudent(Student student) throws SQLException {
        String sql = """
            INSERT INTO students
            (student_id, name, email, password, department, year)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getStudentId());
            ps.setString(2, student.getName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getPassword());
            ps.setString(5, student.getDepartment());
            ps.setInt(6, student.getYear());

            return ps.executeUpdate() == 1;
        }
    }

    public Student login(String email, String password)
            throws SQLException {

        String sql = """
            SELECT * FROM students
            WHERE email = ? AND password = ?
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setStudentId(rs.getString("student_id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setPassword(rs.getString("password"));
                    student.setDepartment(rs.getString("department"));
                    student.setYear(rs.getInt("year"));
                    return student;
                }
            }
        }

        return null;
    }

    public boolean studentIdExists(String studentId)
            throws SQLException {

        return exists(
            "SELECT 1 FROM students WHERE student_id = ?",
            studentId
        );
    }

    public boolean emailExists(String email) throws SQLException {
        return exists(
            "SELECT 1 FROM students WHERE email = ?",
            email
        );
    }

    private boolean exists(String sql, String value)
            throws SQLException {

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, value);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
