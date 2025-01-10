/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.JOptionPane;
import view.LoginView;
import view.TeacherView;

/**
 *
 * @author gh
 */
public class TeacherController {
    private TeacherView view;

    public TeacherController(TeacherView view) {
        this.view = view;

        view.addTakeAttendanceListener(e -> takeAttendance());
        view.addGenerateReportListener(e -> generateReport());
        view.addLogoutListener(e -> logout());
    }

    private void takeAttendance() {
        JOptionPane.showMessageDialog(view, "Taking Attendance...");
    }

    private void viewSchedule() {
        JOptionPane.showMessageDialog(view, "Viewing Class Schedule...");
    }

    private void generateReport() {
        JOptionPane.showMessageDialog(view, "Generating Report...");
    }

    private void logout() {
        view.dispose();
        new LoginView().setVisible(true);
    }
}
