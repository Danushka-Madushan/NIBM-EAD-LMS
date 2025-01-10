/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gh
 */
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentModel {
    private Connection conn;

    public StudentModel() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve Student Information by ID
    public Student getStudentById(int studentId) {
        Student student = null;
        String query = "SELECT s.*, c.name AS class_name FROM students s " +
                       "LEFT JOIN classes c ON s.class_id = c.id WHERE s.id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("class_id"),
                    rs.getString("class_name")  // Get class name from the joined table
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    // Retrieve List of Students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.*, c.name AS class_name FROM students s " +
                       "LEFT JOIN classes c ON s.class_id = c.id";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("class_id"),
                    rs.getString("class_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Retrieve Students by Class ID
    public List<Student> getStudentsByClassId(int classId) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.*, c.name AS class_name FROM students s " +
                       "LEFT JOIN classes c ON s.class_id = c.id WHERE s.class_id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("class_id"),
                    rs.getString("class_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Update Student Information
    public boolean updateStudent(Student student) {
        String query = "UPDATE students SET name = ?, email = ?, class_id = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setInt(3, student.getClassId());
            stmt.setInt(4, student.getId());
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete Student
    public boolean deleteStudent(int studentId) {
        String query = "DELETE FROM students WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Assign Student to a Class
    public boolean assignStudentToClass(int studentId, int classId) {
        String query = "UPDATE students SET class_id = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, classId);
            stmt.setInt(2, studentId);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
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
