/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author gh
 */
public class Grade {
    private int id;
    private int studentId;
    private int subjectId;
    private double grade;

    public Grade(int id, int studentId, int subjectId, double grade) {
        this.id = id;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.grade = grade;
    }

    // Getters
    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getSubjectId() { return subjectId; }
    public double getGrade() { return grade; }

    // Setters
    public void setGrade(double grade) { this.grade = grade; }
}

