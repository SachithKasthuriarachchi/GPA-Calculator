package com.sachith.gpacalculator.model;

public class DashboardObject {

    private double credits;
    private double GPA;
    private int semester;

    public DashboardObject(double credits, double GPA, int semester) {

        this.credits = credits;
        this.GPA = GPA;
        this.semester = semester;
    }

    public double getCredits() {
        return credits;
    }

    public int getSemester() {
        return semester;
    }

    public double getGPA() {
        return GPA;
    }

}
