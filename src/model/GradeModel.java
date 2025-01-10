/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author gh
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeModel {
    private Connection conn;

    public GradeModel() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add or Update Grade
    public boolean addOrUpdateGrade(int studentId, int subjectId, double grade) {
        String query = "INSERT INTO grades (student_id, subject_id, grade) VALUES (?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE grade = VALUES(grade)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, subjectId);
            stmt.setDouble(3, grade);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get Grades by Student
    public List<Grade> getGradesByStudent(int studentId) {
        List<Grade> gradeList = new ArrayList<>();
        String query = "SELECT * FROM grades WHERE student_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                gradeList.add(new Grade(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getInt("subject_id"),
                    rs.getDouble("grade")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gradeList;
    }

    // Get Average Grade by Student
    public double getAverageGrade(int studentId) {
        String query = "SELECT AVG(grade) AS average FROM grades WHERE student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("average");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Delete Grade by ID
    public boolean deleteGrade(int gradeId) {
        String query = "DELETE FROM grades WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, gradeId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Close Connection
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

