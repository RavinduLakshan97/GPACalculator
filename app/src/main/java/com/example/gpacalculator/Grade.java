package com.example.gpacalculator;

public class Grade {

    private String subject;
    private String grade;
    private int credits;
    private double gpa;

    public Grade(String subject, String grade, int credits, double gpa) {
        this.subject = subject;
        this.grade = grade;
        this.credits = credits;
        this.gpa = gpa;
    }

    public String getSubject() {
        return subject;
    }

    public String getGrade() {
        return grade;
    }

    public int getCredits() {
        return credits;
    }

    public double getGpa() {
        return gpa;
    }
}