package com.campusissue.model;

import java.time.LocalDateTime;

public class Report {
    private int reportId;
    private String studentId;
    private String studentName;
    private int problemId;
    private String problemName;
    private Integer workpersonId;
    private String workpersonName;
    private String location;
    private String description;
    private String status;
    private String adminRemark;
    private LocalDateTime createdAt;

    public Report() {
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public Integer getWorkpersonId() {
        return workpersonId;
    }

    public void setWorkpersonId(Integer workpersonId) {
        this.workpersonId = workpersonId;
    }

    public String getWorkpersonName() {
        return workpersonName;
    }

    public void setWorkpersonName(String workpersonName) {
        this.workpersonName = workpersonName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminRemark() {
        return adminRemark;
    }

    public void setAdminRemark(String adminRemark) {
        this.adminRemark = adminRemark;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "\nReport ID: " + reportId
                + "\nStudent ID: " + studentId
                + "\nStudent Name: " + studentName
                + "\nProblem: " + problemName
                + "\nLocation: " + location
                + "\nDescription: " + description
                + "\nStatus: " + status
                + "\nAssigned Worker: "
                + (workpersonName == null ? "Not assigned" : workpersonName)
                + "\nAdmin Remark: "
                + (adminRemark == null ? "None" : adminRemark)
                + "\nCreated At: " + createdAt;
    }
}