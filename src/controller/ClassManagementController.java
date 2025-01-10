/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import view.ManageClasses;
import view.NewClass;

public class ClassManagementController {
    private ManageClasses view;
    private static final String URL = "jdbc:mysql://localhost/lms";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public ClassManagementController(ManageClasses view) {
        this.view = view;

        view.addClassListener(e -> showAddClass());
    }
    
    public static boolean isClassAvailable(String name) {
        String query = "SELECT COUNT(*) FROM subjects WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0; // Username is available if count is 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if an exception occurs
    }

    private void showAddClass() {
        NewClass newClass = new NewClass();
        newClass.setLocationRelativeTo(null);
        newClass.setModal(true);
        newClass.setVisible(true);
    }

    public static void AddClass(String name, int teacher_id, int subject_id, String schedule, String room) throws SQLException {
        String insertSubjectQuery = "INSERT INTO classes (name, teacher_id, subject_id, schedule, room) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Start transaction
            conn.setAutoCommit(false);

            try {
                PreparedStatement teacherStmt = conn.prepareStatement(insertSubjectQuery);

                teacherStmt.setString(1, name);
                teacherStmt.setInt(2, teacher_id);
                teacherStmt.setInt(3, subject_id);
                teacherStmt.setString(4, schedule);
                teacherStmt.setString(5, room);

                teacherStmt.executeUpdate();

                // Commit transaction
                conn.commit();
                System.out.println("User and student added successfully!");
            } catch (Exception e) {
                // Rollback transaction in case of error
                conn.rollback();
                throw e;
            }
        }
    }
}
