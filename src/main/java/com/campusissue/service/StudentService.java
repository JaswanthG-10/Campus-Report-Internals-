package com.campusissue.service;

import com.campusissue.dao.ProblemDAO;
import com.campusissue.dao.ReportDAO;
import com.campusissue.model.Problem;
import com.campusissue.model.Report;
import com.campusissue.model.Student;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class StudentService {

    private final ProblemDAO problemDAO = new ProblemDAO();
    private final ReportDAO reportDAO = new ReportDAO();

    public List<Problem> viewProblems() {
        try {
            return problemDAO.findAll();
        } catch (SQLException e) {
            System.out.println("Database error fetching problems: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public boolean submitReport(Student student, int problemId,
                                String location, String description) {
        if (student == null) {
            System.out.println("Please log in first.");
            return false;
        }

        if (location == null || location.isBlank()) {
            System.out.println("Location cannot be empty.");
            return false;
        }

        try {
            if (problemDAO.findById(problemId) == null) {
                System.out.println("Invalid problem selection.");
                return false;
            }

            int reportId = reportDAO.createReport(
                    student.getStudentId(),
                    problemId,
                    location.trim(),
                    description == null ? "" : description.trim()
            );

            if (reportId > 0) {
                System.out.println("Report submitted successfully.");
                System.out.println("Your Report ID: " + reportId);
                return true;
            }

            System.out.println("Failed to submit report.");
            return false;
        } catch (SQLException e) {
            System.out.println("Database error submitting report: " + e.getMessage());
            return false;
        }
    }

    public List<Report> viewMyReports(Student student) {
        if (student == null) {
            System.out.println("Please log in first.");
            return Collections.emptyList();
        }

        try {
            return reportDAO.findByStudentId(student.getStudentId());
        } catch (SQLException e) {
            System.out.println("Database error fetching your reports: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}