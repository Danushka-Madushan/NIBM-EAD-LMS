/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author gh
 */
import java.util.Date;

public class Attendance {
    private int id;
    private int studentId;
    private int classId;
    private String status;
    private Date date;
    private String relatedName;  // Class name or student name

    public Attendance(int id, int studentId, int classId, String status, Date date, String relatedName) {
        this.id = id;
        this.studentId = studentId;
        this.classId = classId;
        this.status = status;
        this.date = date;
        this.relatedName = relatedName;
    }

    // Getters
    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getClassId() { return classId; }
    public String getStatus() { return status; }
    public Date getDate() { return date; }
    public String getRelatedName() { return relatedName; }
}

