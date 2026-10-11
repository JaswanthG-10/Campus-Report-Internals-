package com.campusissue.service;

import com.campusissue.dao.ReportDAO;
import com.campusissue.model.Report;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ReportService {

    private final ReportDAO reportDAO = new ReportDAO();

    public int createReport(String studentId, int problemId,
                            String location, String description) {
        if (studentId == null || studentId.isBlank()
                || problemId <= 0
                || location == null || location.isBlank()) {
            System.out.println("Invalid report details.");
            return -1;
        }

        try {
            return reportDAO.createReport(
                    studentId, problemId, location.trim(), description
            );
        } catch (SQLException e) {
            System.out.println("Database error creating report: " + e.getMessage());
            return -1;
        }
    }

    public List<Report> getReportsByStudent(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            return Collections.emptyList();
        }

        try {
            return reportDAO.findByStudentId(studentId);
        } catch (SQLException e) {
            System.out.println("Database error fetching reports: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Report> getAllReports() {
        try {
            return reportDAO.findAll();
        } catch (SQLException e) {
            System.out.println("Database error fetching all reports: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public Report getReportById(int reportId) {
        if (reportId <= 0) {
            return null;
        }

        try {
            return reportDAO.findById(reportId);
        } catch (SQLException e) {
            System.out.println("Database error fetching report: " + e.getMessage());
            return null;
        }
    }
}