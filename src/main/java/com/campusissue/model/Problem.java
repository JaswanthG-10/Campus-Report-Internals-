package com.campusissue.model;

public class Problem {
    private int problemId;
    private String problemName;
    private String category;

    public Problem() {
    }

    public Problem(int problemId, String problemName, String category) {
        this.problemId = problemId;
        this.problemName = problemName;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return problemId + ". " + problemName + " (" + category + ")";
    }
}