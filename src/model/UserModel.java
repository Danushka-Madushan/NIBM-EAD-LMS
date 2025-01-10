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

public class UserModel {
    public String validateUser(String username, String password) {
        String role = null;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "")) {
            PreparedStatement pst = con.prepareStatement("SELECT role FROM users WHERE username=? AND password=?");
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }
}
