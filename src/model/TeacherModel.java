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

public class TeacherModel {
    private Connection conn;

    public TeacherModel() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve Teacher Information by ID
    public Teacher getTeacherById(int teacherId) {
        Teacher teacher = null;
        String query = "SELECT * FROM teachers WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                teacher = new Teacher(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("subject")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    // Retrieve List of All Teachers
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT * FROM teachers";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                teachers.add(new Teacher(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("subject")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    // Update Teacher Information
    public boolean updateTeacher(Teacher teacher) {
        String query = "UPDATE teachers SET name = ?, email = ?, subject = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getEmail());
            stmt.setString(3, teacher.getSubject());
            stmt.setInt(4, teacher.getId());
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete Teacher
    public boolean deleteTeacher(int teacherId) {
        String query = "DELETE FROM teachers WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, teacherId);
            
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
