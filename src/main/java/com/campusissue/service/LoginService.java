package com.campusissue.service;

import com.campusissue.dao.StudentDAO;
import com.campusissue.dao.AdminDAO;
import com.campusissue.model.Student;
import com.campusissue.model.Admin;
import com.campusissue.util.ValidationUtil;

import java.sql.SQLException;

public class LoginService {

    private final StudentDAO studentDAO = new StudentDAO();
    private final AdminDAO adminDAO = new AdminDAO();

    public boolean registerStudent(Student student) {
        if (student == null) {
            System.out.println("Student details cannot be empty.");
            return false;
        }

        if (student.getEmail() == null ||
            !ValidationUtil.isValidCollegeEmail(student.getEmail())) {
            System.out.println("Registration failed: Only college email addresses ending with @rajalakshmi.edu.in are allowed.");
            return false;
        }

        if (student.getStudentId() == null ||
            !ValidationUtil.isValidStudentId(student.getStudentId())) {
            System.out.println("Student ID must contain exactly 9 digits.");
            return false;
        }

        if (student.getName() == null || student.getName().isBlank()
                || student.getPassword() == null
                || student.getPassword().isBlank()
                || student.getDepartment() == null
                || student.getDepartment().isBlank()
                || !ValidationUtil.isValidYear(student.getYear())) {
            System.out.println("Please enter valid student details.");
            return false;
        }

        // Normalize email
        student.setEmail(ValidationUtil.normalizeEmail(student.getEmail()));

        try {
            if (studentDAO.emailExists(student.getEmail())) {
                System.out.println("Email is already registered.");
                return false;
            }

            if (studentDAO.studentIdExists(student.getStudentId())) {
                System.out.println("Student ID is already registered.");
                return false;
            }

            return studentDAO.registerStudent(student);
        } catch (SQLException e) {
            System.out.println("Database error during registration: " + e.getMessage());
            return false;
        }
    }

    public Student studentLogin(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            System.out.println("Email and password cannot be empty.");
            return null;
        }

        String normalizedEmail = ValidationUtil.normalizeEmail(email);

        if (!ValidationUtil.isValidCollegeEmail(normalizedEmail)) {
            System.out.println("Access denied: Email must be an official college email ending with @rajalakshmi.edu.in.");
            return null;
        }

        try {
            return studentDAO.login(normalizedEmail, password);
        } catch (SQLException e) {
            System.out.println("Database error during student login: " + e.getMessage());
            return null;
        }
    }

    public Admin adminLogin(String username, String password) {
        if (username == null || password == null
                || username.isBlank() || password.isBlank()) {
            System.out.println("Enter the admin username and password.");
            return null;
        }

        try {
            return adminDAO.login(username.trim(), password);
        } catch (SQLException e) {
            System.out.println("Database error during admin login: " + e.getMessage());
            return null;
        }
    }
}
