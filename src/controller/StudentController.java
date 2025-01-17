package controller;


import javax.swing.JOptionPane;
import view.LoginView;
import view.StudentView;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gh
 */
public class StudentController {
    private StudentView view;

    public StudentController(StudentView view) {
        this.view = view;

        view.addViewAttendanceListener(e -> viewAttendance());
        view.addPayFeesListener(e -> payFees());
        view.addLogoutListener(e -> logout());
    }

    private void viewAttendance() {
        ReportController.generateAttendenceReport();
    }

    private void payFees() {
        JOptionPane.showMessageDialog(view, "Paying Fees...");
    }

    private void viewSchedule() {
        JOptionPane.showMessageDialog(view, "Viewing Class Schedule...");
    }

    private void logout() {
        view.dispose();
        new LoginView().setVisible(true);
    }
}
