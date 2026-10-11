
package com.campusissue.dao;

import com.campusissue.database.DatabaseConnection;
import com.campusissue.model.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    // Create a report and return its generated ID.
    public int createReport(
            String studentId,
            int problemId,
            String location,
            String description) throws SQLException {

        String sql = """
            INSERT INTO reports
            (student_id, problem_id, location, description)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, studentId);
            ps.setInt(2, problemId);
            ps.setString(3, location);
            ps.setString(4, description);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        return -1;
    }

    // Retrieve reports belonging to one student.
    public List<Report> findByStudentId(String studentId)
            throws SQLException {

        String sql = baseQuery()
                + " WHERE r.student_id = ? ORDER BY r.created_at DESC";

        List<Report> reports = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reports.add(mapReport(rs));
                }
            }
        }

        return reports;
    }

    // Retrieve all reports for the admin.
    public List<Report> findAll() throws SQLException {
        List<Report> reports = new ArrayList<>();

        String sql = baseQuery() + " ORDER BY r.created_at DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                reports.add(mapReport(rs));
            }
        }

        return reports;
    }

    // Retrieve one report by ID.
    public Report findById(int reportId) throws SQLException {
        String sql = baseQuery() + " WHERE r.report_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reportId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapReport(rs);
                }
            }
        }

        return null;
    }

    // Assign a workperson and update the status.
    public boolean assignWorkperson(
            int reportId,
            int workpersonId,
            String remark) throws SQLException {

        String sql = """
            UPDATE reports
            SET workperson_id = ?,
                status = 'ASSIGNED',
                admin_remark = ?
            WHERE report_id = ?
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, workpersonId);
            ps.setString(2, remark);
            ps.setInt(3, reportId);

            return ps.executeUpdate() == 1;
        }
    }

    // Update the report status and admin remark.
    public boolean updateStatus(
            int reportId,
            String status,
            String remark) throws SQLException {

        String sql = """
            UPDATE reports
            SET status = ?, admin_remark = ?
            WHERE report_id = ?
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, remark);
            ps.setInt(3, reportId);

            return ps.executeUpdate() == 1;
        }
    }

    // Shared query used by report retrieval methods.
    private String baseQuery() {
        return """
            SELECT
                r.report_id,
                r.student_id,
                s.name AS student_name,
                r.problem_id,
                p.problem_name,
                r.workperson_id,
                w.name AS workperson_name,
                r.location,
                r.description,
                r.status,
                r.admin_remark,
                r.created_at
            FROM reports r
            JOIN students s ON r.student_id = s.student_id
            JOIN problems p ON r.problem_id = p.problem_id
            LEFT JOIN workpersons w
                ON r.workperson_id = w.workperson_id
            """;
    }

    // Convert a database row into a Report object.
    private Report mapReport(ResultSet rs) throws SQLException {
        Report report = new Report();

        report.setReportId(rs.getInt("report_id"));
        report.setStudentId(rs.getString("student_id"));
        report.setStudentName(rs.getString("student_name"));
        report.setProblemId(rs.getInt("problem_id"));
        report.setProblemName(rs.getString("problem_name"));

        int workpersonId = rs.getInt("workperson_id");
        report.setWorkpersonId(
            rs.wasNull() ? null : workpersonId
        );

        report.setWorkpersonName(rs.getString("workperson_name"));
        report.setLocation(rs.getString("location"));
        report.setDescription(rs.getString("description"));
        report.setStatus(rs.getString("status"));
        report.setAdminRemark(rs.getString("admin_remark"));

        Timestamp timestamp = rs.getTimestamp("created_at");
        report.setCreatedAt(
            timestamp == null ? null : timestamp.toLocalDateTime()
        );

        return report;
    }
}
