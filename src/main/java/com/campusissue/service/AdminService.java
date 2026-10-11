package com.campusissue.service;

import com.campusissue.dao.ReportDAO;
import com.campusissue.dao.WorkpersonDAO;
import com.campusissue.model.Report;
import com.campusissue.model.Workperson;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class AdminService {

    private final ReportDAO reportDAO = new ReportDAO();
    private final WorkpersonDAO workpersonDAO = new WorkpersonDAO();

    public List<Report> viewAllReports() {
        try {
            return reportDAO.findAll();
        } catch (SQLException e) {
            System.out.println("Database error fetching reports: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Workperson> viewWorkpersons() {
        try {
            return workpersonDAO.findAll();
        } catch (SQLException e) {
            System.out.println("Database error fetching workpersons: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public boolean assignWorkperson(int reportId, int workpersonId,
                                    String remark) {
        try {
            Report report = reportDAO.findById(reportId);
            Workperson worker = workpersonDAO.findById(workpersonId);

            if (report == null) {
                System.out.println("Report not found.");
                return false;
            }

            if (worker == null) {
                System.out.println("Workperson not found.");
                return false;
            }

            boolean success = reportDAO.assignWorkperson(
                    reportId, workpersonId, remark
            );

            if (success) {
                System.out.println("Workperson assigned successfully.");
            } else {
                System.out.println("Failed to assign workperson.");
            }

            return success;
        } catch (SQLException e) {
            System.out.println("Database error assigning workperson: " + e.getMessage());
            return false;
        }
    }

    public boolean updateReportStatus(int reportId, String status,
                                      String remark) {
        try {
            if (reportDAO.findById(reportId) == null) {
                System.out.println("Report not found.");
                return false;
            }

            if (status == null ||
                    !List.of("SUBMITTED", "ASSIGNED", "IN_PROGRESS",
                             "COMPLETED", "REJECTED").contains(status)) {
                System.out.println("Invalid report status.");
                return false;
            }

            boolean success = reportDAO.updateStatus(
                    reportId, status, remark
            );

            if (success) {
                System.out.println("Report status updated successfully.");
            } else {
                System.out.println("Failed to update report status.");
            }

            return success;
        } catch (SQLException e) {
            System.out.println("Database error updating status: " + e.getMessage());
            return false;
        }
    }
}