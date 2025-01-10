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
import view.ManageSubjects;
import view.NewSubject;

/**
 *
 * @author gh
 */
public class SubjectManagementController {
    private ManageSubjects view;
    private static final String URL = "jdbc:mysql://localhost/lms";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public SubjectManagementController(ManageSubjects view) {
        this.view = view;

        view.addSubjectListener(e -> showAddSubject());
    }
    
    public static boolean isSubjectAvailable(String name) {
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
    
    public static boolean deleteById(int Id) {
        String deleteUserQuery = "DELETE FROM subjects WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserQuery)) {

            // Fetch the user_id associated with the teacherId
            deleteUserStmt.setInt(1, Id);
            deleteUserStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return false if no user was deleted or an error occurred
        return false;
    }
    
    public static void AddSubject(String name, int teacher_id) throws SQLException {
        String insertSubjectQuery = "INSERT INTO subjects (name, teacher_id) VALUES (?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Start transaction
            conn.setAutoCommit(false);

            try {
                PreparedStatement teacherStmt = conn.prepareStatement(insertSubjectQuery);

                teacherStmt.setString(1, name);
                teacherStmt.setInt(2, teacher_id);

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

    private void showAddSubject() {
        NewSubject newSubject = new NewSubject();
        newSubject.setLocationRelativeTo(null);
        newSubject.setModal(true);
        newSubject.setVisible(true);
    }
}
