package main;

import com.formdev.flatlaf.FlatDarculaLaf;
import controller.AdminController;
import controller.StudentController;
import controller.LoginController;
import controller.TeacherController;
import javax.swing.JOptionPane;
import model.UserModel;
import view.AdminView;
import view.LoginView;
import view.StudentView;
import view.TeacherView;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author gh
 */
public class Main {
    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        LoginView loginView = new LoginView();
        UserModel userModel = new UserModel();
        LoginController loginController = new LoginController(loginView, userModel);

        // Attach role-based navigation
        loginController.setLoginSuccessListener((username, role) -> {
            loginView.dispose();

            switch (role.toLowerCase()) {
                case "admin":
                    AdminView adminView = new AdminView();
                    new AdminController(adminView);
                    adminView.setLocationRelativeTo(null);
                    adminView.setVisible(true);
                    break;
                case "teacher":
                    TeacherView teacherView = new TeacherView();
                    new TeacherController(teacherView);
                    teacherView.setLocationRelativeTo(null);
                    teacherView.setVisible(true);
                    break;
                case "student":
                    StudentView studentView = new StudentView();
                    new StudentController(studentView);
                    studentView.setLocationRelativeTo(null);
                    studentView.setVisible(true);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Unknown Role");
                    System.exit(0);
            }
        });

        // Show Login Screen
        loginView.setLocationRelativeTo(null);
        loginView.setVisible(true);
    }
}
