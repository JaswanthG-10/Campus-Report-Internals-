-- CampusIssueSystem: Database Schema
-- MySQL 8.0+

CREATE DATABASE IF NOT EXISTS campus_issue_system;
USE campus_issue_system;

-- 1. Students
CREATE TABLE IF NOT EXISTS students (
    student_id VARCHAR(9) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    department VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Administrators
CREATE TABLE IF NOT EXISTS admins (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Problem categories
CREATE TABLE IF NOT EXISTS problems (
    problem_id INT AUTO_INCREMENT PRIMARY KEY,
    problem_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL
);

-- 4. Workpersons
CREATE TABLE IF NOT EXISTS workpersons (
    workperson_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    phone VARCHAR(20)
);

-- 5. Student reports
CREATE TABLE IF NOT EXISTS reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(9) NOT NULL,
    problem_id INT NOT NULL,
    workperson_id INT NULL,
    location VARCHAR(200) NOT NULL,
    description TEXT,
    status VARCHAR(30) DEFAULT 'SUBMITTED',
    admin_remark TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reports_student
        FOREIGN KEY (student_id)
        REFERENCES students(student_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_reports_problem
        FOREIGN KEY (problem_id)
        REFERENCES problems(problem_id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_reports_workperson
        FOREIGN KEY (workperson_id)
        REFERENCES workpersons(workperson_id)
        ON DELETE SET NULL
);

-- Indexes for performance
CREATE INDEX idx_reports_student ON reports(student_id);
CREATE INDEX idx_reports_status ON reports(status);
CREATE INDEX idx_reports_workperson ON reports(workperson_id);
