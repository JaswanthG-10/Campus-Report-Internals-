package com.campusissue.util;

import java.util.Set;

public final class ValidationUtil {

    private static final String COLLEGE_DOMAIN = "@rajalakshmi.edu.in";

    private static final Set<String> VALID_STATUSES = Set.of(
            "SUBMITTED",
            "ASSIGNED",
            "IN_PROGRESS",
            "COMPLETED",
            "REJECTED"
    );

    // Prevent object creation
    private ValidationUtil() {
    }

    // Student ID must contain exactly 9 digits
    public static boolean isValidStudentId(String studentId) {
        return studentId != null && studentId.matches("\\d{9}");
    }

    // Email must use the college domain
    public static boolean isValidCollegeEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        String normalizedEmail = email.trim().toLowerCase();

        return normalizedEmail.endsWith(COLLEGE_DOMAIN)
                && normalizedEmail.length() > COLLEGE_DOMAIN.length()
                && normalizedEmail.matches("^[a-z0-9._%+-]+@rajalakshmi\\.edu\\.in$");
    }

    // Check whether a password is present
    public static boolean isValidPassword(String password) {
        return password != null && !password.isBlank();
    }

    // Check whether a name is present
    public static boolean isValidName(String name) {
        return name != null && !name.isBlank();
    }

    // Student year must be between 1 and 4
    public static boolean isValidYear(int year) {
        return year >= 1 && year <= 4;
    }

    // Department must not be empty
    public static boolean isValidDepartment(String department) {
        return department != null && !department.isBlank();
    }

    // Report location must not be empty
    public static boolean isValidLocation(String location) {
        return location != null && !location.isBlank();
    }

    // Problem ID must be positive
    public static boolean isValidProblemId(int problemId) {
        return problemId > 0;
    }

    // Report ID must be positive
    public static boolean isValidReportId(int reportId) {
        return reportId > 0;
    }

    // Workperson ID must be positive
    public static boolean isValidWorkpersonId(int workpersonId) {
        return workpersonId > 0;
    }

    // Check allowed report statuses
    public static boolean isValidStatus(String status) {
        return status != null
                && VALID_STATUSES.contains(
                        status.trim().toUpperCase()
                );
    }

    // Optional description can be empty
    public static boolean isValidDescription(String description) {
        return description == null || description.length() <= 2000;
    }

    // Normalize email before storing or querying
    public static String normalizeEmail(String email) {
        if (email == null) {
            return null;
        }

        return email.trim().toLowerCase();
    }

    // Normalize report status
    public static String normalizeStatus(String status) {
        if (status == null) {
            return null;
        }

        return status.trim().toUpperCase();
    }
}