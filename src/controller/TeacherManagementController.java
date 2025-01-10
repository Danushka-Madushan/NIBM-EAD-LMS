/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.*;
import view.ManageTeachers;
import view.NewTeacher;

/**
 *
 * @author gh
 */
public class TeacherManagementController {
    private ManageTeachers view;
    private static final String URL = "jdbc:mysql://localhost/lms";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public TeacherManagementController(ManageTeachers view) {
        this.view = view;

        view.addTeacherListener(e -> showAddTeacher());
    }

    private void showAddTeacher() {
        NewTeacher newTeacher = new NewTeacher();
        newTeacher.setLocationRelativeTo(null);
        newTeacher.setModal(true);
        newTeacher.setVisible(true);
    }
     
    public static boolean deleteUserByTeacherId(int teacherId) {
        String fetchUserIdQuery = "SELECT user_id FROM teachers WHERE id = ?";
        String deleteUserQuery = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement fetchUserIdStmt = conn.prepareStatement(fetchUserIdQuery);
             PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserQuery)) {

            // Fetch the user_id associated with the teacherId
            fetchUserIdStmt.setInt(1, teacherId);
            try (ResultSet rs = fetchUserIdStmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");

                    // Delete the user with the fetched user_id
                    deleteUserStmt.setInt(1, userId);
                    int rowsAffected = deleteUserStmt.executeUpdate();

                    // Return true if deletion was successful
                    return rowsAffected > 0;
                } else {
                    System.out.println("No teacher found with ID: " + teacherId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return false if no user was deleted or an error occurred
        return false;
    }
     
    public static String[] getTeacherDetailsById(int id) {
        String query = "SELECT name, department, email, phone FROM teachers WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the ID parameter
            pstmt.setInt(1, id);

            // Execute the query
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Extract details into an array
                    String[] details = new String[4];
                    details[0] = rs.getString("name");
                    details[1] = rs.getString("department");
                    details[2] = rs.getString("email");
                    details[3] = rs.getString("phone");
                    return details;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return null if no data found or an exception occurred
        return null;
    }
    
    public static void updateTeacher(String id, String name, String department, String email, String phone) throws SQLException {
        String insertTeacherQuery = "UPDATE teachers SET name = ?, department= ?, email= ?, phone= ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Start transaction
            conn.setAutoCommit(false);

            try {
                PreparedStatement teacherStmt = conn.prepareStatement(insertTeacherQuery);

                teacherStmt.setString(1, name);
                teacherStmt.setString(2, department);
                teacherStmt.setString(3, email);
                teacherStmt.setString(4, phone);
                teacherStmt.setInt(5, Integer.parseInt(id));

                teacherStmt.executeUpdate();

                // Commit transaction
                conn.commit();
                System.out.println("User and teacher added successfully!");
            } catch (Exception e) {
                // Rollback transaction in case of error
                conn.rollback();
                throw e;
            }
        }
    }
    
    public static void addTeacher(String username, String userPassword, String role,
                                     String name, String department, String email, String phone) throws SQLException {
        // SQL Queries
        String insertUserQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        String insertTeacherQuery = "INSERT INTO teachers (user_id, name, department, email, phone) VALUES (?, ?, ?, ?, ?)";

        // Establish connection
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Start transaction
            conn.setAutoCommit(false);

            try (PreparedStatement userStmt = conn.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement teacherStmt = conn.prepareStatement(insertTeacherQuery)) {

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
                    teacherStmt.setString(3, department);
                    teacherStmt.setString(4, email);
                    teacherStmt.setString(5, phone);

                    teacherStmt.executeUpdate();

                    // Commit transaction
                    conn.commit();
                    System.out.println("User and teacher added successfully!");
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
