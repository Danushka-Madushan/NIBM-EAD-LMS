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
import java.sql.Statement;
import view.ManageStudents;
import view.NewStudent;

/**
 *
 * @author gh
 */
public class StudentManagementController {
    private ManageStudents view;
    private static final String URL = "jdbc:mysql://localhost/lms";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public StudentManagementController(ManageStudents view) {
        this.view = view;

        view.addStudentsListener(e -> showAddStudent());
    }

    private void showAddStudent() {
        NewStudent newStudent = new NewStudent();
        newStudent.setLocationRelativeTo(null);
        newStudent.setModal(true);
        newStudent.setVisible(true);
    }
    
    public static String[] getStudentDetailsById(int id) {
        String query = "SELECT name, email, phone FROM students WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the ID parameter
            pstmt.setInt(1, id);

            // Execute the query
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Extract details into an array
                    String[] details = new String[3];
                    details[0] = rs.getString("name");
                    details[1] = rs.getString("email");
                    details[2] = rs.getString("phone");
                    return details;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return null if no data found or an exception occurred
        return null;
    }
    
    public static void updateStudent(String id, String name, String email, String phone) throws SQLException {
        String insertStudentQuery = "UPDATE students SET name = ?, email= ?, phone= ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Start transaction
            conn.setAutoCommit(false);

            try {
                PreparedStatement teacherStmt = conn.prepareStatement(insertStudentQuery);

                teacherStmt.setString(1, name);
                teacherStmt.setString(2, email);
                teacherStmt.setString(3, phone);
                teacherStmt.setInt(4, Integer.parseInt(id));

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
    
    public static boolean deleteUserByStudentId(int studentId) {
        String fetchUserIdQuery = "SELECT user_id FROM students WHERE id = ?";
        String deleteUserQuery = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement fetchUserIdStmt = conn.prepareStatement(fetchUserIdQuery);
             PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserQuery)) {

            // Fetch the user_id associated with the teacherId
            fetchUserIdStmt.setInt(1, studentId);
            try (ResultSet rs = fetchUserIdStmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");

                    // Delete the user with the fetched user_id
                    deleteUserStmt.setInt(1, userId);
                    int rowsAffected = deleteUserStmt.executeUpdate();

                    // Return true if deletion was successful
                    return rowsAffected > 0;
                } else {
                    System.out.println("No students found with ID: " + studentId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return false if no user was deleted or an error occurred
        return false;
    }
    
    public static void addStudent(String username, String userPassword, String role,
                                     String name, String email, String phone) throws SQLException {
        // SQL Queries
        String insertUserQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        String insertStudentQuery = "INSERT INTO students (user_id, name, email, phone) VALUES (?, ?, ?, ?)";

        // Establish connection
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Start transaction
            conn.setAutoCommit(false);

            try (PreparedStatement userStmt = conn.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement teacherStmt = conn.prepareStatement(insertStudentQuery)) {

                // Insert into `users` table
                userStmt.setString(1, username);
                userStmt.setString(2, userPassword);
                userStmt.setString(3, role);
                userStmt.executeUpdate();

                // Retrieve the generated `id`
                ResultSet rs = userStmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);

                    // Insert into `teachers` table
                    teacherStmt.setInt(1, userId);
                    teacherStmt.setString(2, name);
                    teacherStmt.setString(3, email);
                    teacherStmt.setString(4, phone);

                    teacherStmt.executeUpdate();

                    // Commit transaction
                    conn.commit();
                    System.out.println("User and student added successfully!");
                } else {
                    throw new SQLException("Failed to retrieve user ID.");
                }
            } catch (Exception e) {
                // Rollback transaction in case of error
                conn.rollback();
                throw e;
            }
        }
    }
}
