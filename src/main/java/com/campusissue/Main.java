package com.campusissue;

import com.campusissue.model.*;
import com.campusissue.service.*;
import com.campusissue.util.ValidationUtil;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final LoginService loginService = new LoginService();
    private static final StudentService studentService = new StudentService();
    private static final AdminService adminService = new AdminService();

    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("       CAMPUS ISSUE SYSTEM");
        System.out.println("======================================");

        while (true) {
            System.out.println("\n1. Student");
            System.out.println("2. Admin");
            System.out.println("3. Exit");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> studentPortal();
                case 2 -> adminPortal();
                case 3 -> {
                    System.out.println("Thank you for using CampusIssueSystem.");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // ================= STUDENT PORTAL =================

    private static void studentPortal() {
        while (true) {
            System.out.println("\n--- STUDENT PORTAL ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Back");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> registerStudent();
                case 2 -> {
                    Student student = studentLogin();

                    if (student != null) {
                        studentMenu(student);
                    }
                }
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void registerStudent() {
        System.out.println("\n--- STUDENT REGISTRATION ---");

        String studentId;
        while (true) {
            studentId = readLine("Enter 9-digit student ID: ").trim();

            if (ValidationUtil.isValidStudentId(studentId)) {
                break;
            }

            System.out.println("Invalid ID. Enter exactly 9 digits.");
        }

        String name = readNonEmpty("Enter name: ");

        String email;
        while (true) {
            email = readLine("Enter college email: ").trim();

            if (ValidationUtil.isValidCollegeEmail(email)) {
                break;
            }

            System.out.println(
                    "Invalid email. Must be an official college email ending with @rajalakshmi.edu.in."
            );
        }

        String password = readNonEmpty("Create password: ");
        String department = readNonEmpty("Enter department: ");

        int year;
        do {
            year = readInt("Enter year (1-4): ");

            if (!ValidationUtil.isValidYear(year)) {
                System.out.println("Year must be between 1 and 4.");
            }
        } while (!ValidationUtil.isValidYear(year));

        Student student = new Student(
                studentId,
                name,
                ValidationUtil.normalizeEmail(email),
                password,
                department,
                year
        );

        if (loginService.registerStudent(student)) {
            System.out.println("Registration successful. Please log in.");
        } else {
            System.out.println("Registration failed. Check the details.");
        }
    }

    private static Student studentLogin() {
        System.out.println("\n--- STUDENT LOGIN ---");

        String email = readLine("College email: ").trim();

        if (!ValidationUtil.isValidCollegeEmail(email)) {
            System.out.println("Invalid email. Only official college email IDs ending with @rajalakshmi.edu.in are allowed.");
            return null;
        }

        String password = readLine("Password: ");

        Student student = loginService.studentLogin(email, password);

        if (student == null) {
            System.out.println("Login failed. Check your credentials.");
        } else {
            System.out.println("Welcome, " + student.getName() + "!");
        }

        return student;
    }

    private static void studentMenu(Student student) {
        while (true) {
            System.out.println("\n--- STUDENT DASHBOARD ---");
            System.out.println("1. View available problems");
            System.out.println("2. Submit a report");
            System.out.println("3. View my reports");
            System.out.println("4. Logout");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> viewProblems();
                case 2 -> submitReport(student);
                case 3 -> viewMyReports(student);
                case 4 -> {
                    System.out.println("Logged out successfully.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void viewProblems() {
        System.out.println("\n--- AVAILABLE PROBLEMS ---");

        List<Problem> problems = studentService.viewProblems();

        if (problems == null || problems.isEmpty()) {
            System.out.println("No problems are available.");
            return;
        }

        for (Problem problem : problems) {
            System.out.println(problem);
        }
    }

    private static void submitReport(Student student) {
        System.out.println("\n--- SUBMIT CAMPUS ISSUE ---");

        List<Problem> problems = studentService.viewProblems();

        if (problems == null || problems.isEmpty()) {
            System.out.println("No problem categories are available.");
            return;
        }

        for (Problem problem : problems) {
            System.out.println(problem);
        }

        int problemId = readInt("Enter problem ID: ");
        String location = readNonEmpty("Enter issue location: ");
        String description = readLine("Describe the issue (optional): ");

        if (!ValidationUtil.isValidDescription(description)) {
            System.out.println("Description cannot exceed 2000 characters.");
            return;
        }

        studentService.submitReport(
                student, problemId, location, description
        );
    }

    private static void viewMyReports(Student student) {
        System.out.println("\n--- MY REPORTS ---");

        List<Report> reports = studentService.viewMyReports(student);

        if (reports == null || reports.isEmpty()) {
            System.out.println("You have not submitted any reports.");
            return;
        }

        for (Report report : reports) {
            System.out.println("--------------------------------------");
            System.out.println(report);
        }
    }

    // ================= ADMIN PORTAL =================

    private static void adminPortal() {
        System.out.println("\n--- ADMIN LOGIN ---");

        String username = readLine("Admin username: ").trim();
        String password = readLine("Admin password: ");

        Admin admin = loginService.adminLogin(username, password);

        if (admin == null) {
            System.out.println("Invalid admin credentials.");
            return;
        }

        System.out.println("Admin login successful.");

        adminMenu();
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN DASHBOARD ---");
            System.out.println("1. View all reports");
            System.out.println("2. Assign workperson");
            System.out.println("3. Update report status");
            System.out.println("4. View workpersons");
            System.out.println("5. Logout");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> viewAllReports();
                case 2 -> assignWorkperson();
                case 3 -> updateReportStatus();
                case 4 -> viewWorkpersons();
                case 5 -> {
                    System.out.println("Admin logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void viewAllReports() {
        System.out.println("\n--- ALL CAMPUS REPORTS ---");

        List<Report> reports = adminService.viewAllReports();

        if (reports == null || reports.isEmpty()) {
            System.out.println("No reports found.");
            return;
        }

        for (Report report : reports) {
            System.out.println("--------------------------------------");
            System.out.println(report);
        }
    }

    private static void assignWorkperson() {
        System.out.println("\n--- ASSIGN WORKPERSON ---");

        List<Report> reports = adminService.viewAllReports();

        if (reports == null || reports.isEmpty()) {
            System.out.println("No reports available.");
            return;
        }

        for (Report report : reports) {
            System.out.println(
                    "Report ID: " + report.getReportId()
                    + " | Problem: " + report.getProblemName()
                    + " | Status: " + report.getStatus()
            );
        }

        int reportId = readInt("Enter report ID: ");

        List<Workperson> workers = adminService.viewWorkpersons();

        if (workers == null || workers.isEmpty()) {
            System.out.println("No workpersons available.");
            return;
        }

        for (Workperson worker : workers) {
            System.out.println(worker);
        }

        int workpersonId = readInt("Enter workperson ID: ");
        String remark = readLine("Admin remark (optional): ");

        adminService.assignWorkperson(reportId, workpersonId, remark);
    }

    private static void updateReportStatus() {
        System.out.println("\n--- UPDATE REPORT STATUS ---");

        int reportId = readInt("Enter report ID: ");

        System.out.println("1. SUBMITTED");
        System.out.println("2. ASSIGNED");
        System.out.println("3. IN_PROGRESS");
        System.out.println("4. COMPLETED");
        System.out.println("5. REJECTED");

        int choice = readInt("Choose new status: ");

        String status = switch (choice) {
            case 1 -> "SUBMITTED";
            case 2 -> "ASSIGNED";
            case 3 -> "IN_PROGRESS";
            case 4 -> "COMPLETED";
            case 5 -> "REJECTED";
            default -> null;
        };

        if (status == null) {
            System.out.println("Invalid status selection.");
            return;
        }

        String remark = readLine("Admin remark (optional): ");

        adminService.updateReportStatus(reportId, status, remark);
    }

    private static void viewWorkpersons() {
        System.out.println("\n--- WORKPERSONS ---");

        List<Workperson> workers = adminService.viewWorkpersons();

        if (workers == null || workers.isEmpty()) {
            System.out.println("No workpersons found.");
            return;
        }

        for (Workperson worker : workers) {
            System.out.println(worker);
        }
    }

    // ================= INPUT HELPERS =================

    private static String readLine(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    private static String readNonEmpty(String message) {
        while (true) {
            String value = readLine(message).trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field cannot be empty. Try again.");
        }
    }

    private static int readInt(String message) {
        while (true) {
            String value = readLine(message).trim();

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid integer.");
            }
        }
    }
}
