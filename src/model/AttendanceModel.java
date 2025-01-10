package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceModel {
    private Connection conn;

    public AttendanceModel() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Record Attendance (Ensure Student Belongs to Class)
    public boolean recordAttendance(int studentId, int classId, String status) {
        String validateQuery = "SELECT * FROM students WHERE id = ? AND class_id = ?";
        String insertQuery = "INSERT INTO attendance (student_id, class_id, status, date) VALUES (?, ?, ?, CURDATE())";

        try (PreparedStatement validateStmt = conn.prepareStatement(validateQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            validateStmt.setInt(1, studentId);
            validateStmt.setInt(2, classId);
            ResultSet rs = validateStmt.executeQuery();

            if (rs.next()) {  // Only record attendance if student belongs to class
                insertStmt.setInt(1, studentId);
                insertStmt.setInt(2, classId);
                insertStmt.setString(3, status);

                int rowsInserted = insertStmt.executeUpdate();
                return rowsInserted > 0;
            } else {
                System.out.println("Student does not belong to this class.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get Attendance by Student ID (With Class Details)
    public List<Attendance> getAttendanceByStudent(int studentId) {
        List<Attendance> attendanceList = new ArrayList<>();
        String query = "SELECT a.*, c.name AS class_name FROM attendance a " +
                       "JOIN classes c ON a.class_id = c.id WHERE a.student_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                attendanceList.add(new Attendance(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getInt("class_id"),
                    rs.getString("status"),
                    rs.getDate("date"),
                    rs.getString("class_name")  // Class name for display
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendanceList;
    }

    // Get Attendance for a Class
    public List<Attendance> getAttendanceByClass(int classId) {
        List<Attendance> attendanceList = new ArrayList<>();
        String query = "SELECT a.*, s.name AS student_name FROM attendance a " +
                       "JOIN students s ON a.student_id = s.id WHERE a.class_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                attendanceList.add(new Attendance(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getInt("class_id"),
                    rs.getString("status"),
                    rs.getDate("date"),
                    rs.getString("student_name")  // Include student name for class attendance
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendanceList;
    }

    // Update Attendance Record
    public boolean updateAttendance(int attendanceId, String status) {
        String query = "UPDATE attendance SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, attendanceId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete Attendance Record
    public boolean deleteAttendance(int attendanceId) {
        String query = "DELETE FROM attendance WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, attendanceId);

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
