package com.campusissue.model;

public class Student extends User {
    private String studentId;
    private String name;
    private String email;
    private String department;
    private int year;

    public Student() {
    }

    public Student(String studentId, String name, String email,
                   String password, String department, int year) {
        super(email, password);
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.year = year;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        setUsername(email);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}