package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import model.UserModel;
import view.AdminView;
import view.LoginView;
import view.StudentView;
import view.TeacherView;
import java.util.function.BiConsumer;

public class LoginController {
    private static final String URL = "jdbc:mysql://localhost/lms";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private LoginView view;
    private UserModel model;
    private BiConsumer<String, String> loginSuccessListener;

    public LoginController(LoginView view, UserModel model) {
        this.view = view;
        this.model = model;

        view.addLoginListener(e -> authenticate());
    }
    
    public static boolean isUsernameAvailable(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);

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

    private void authenticate() {
        String username = view.getUsername();
        String password = view.getPassword();
        String role = model.validateUser(username, password);

        if (role != null) {
            JOptionPane.showMessageDialog(view, "Login Successful!");
            
            // Trigger the listener if it's set
            if (loginSuccessListener != null) {
                loginSuccessListener.accept(username, role);
            } else {
                // Fallback to default behavior if no listener is set
                switch (role) {
                    case "admin":
                        new AdminView().setVisible(true);
                        break;
                    case "teacher":
                        new TeacherView().setVisible(true);
                        break;
                    case "student":
                        new StudentView().setVisible(true);
                        break;
                }
            }
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Invalid Credentials");
        }
    }

    // Set listener for successful login
    public void setLoginSuccessListener(BiConsumer<String, String> listener) {
        this.loginSuccessListener = listener;
    }
}
