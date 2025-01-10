/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.JOptionPane;
import view.AdminView;
import view.ManageClasses;
import view.LoginView;
import view.ManageStudents;
import view.ManageSubjects;
import view.ManageTeachers;

/**
 *
 * @author gh
 */
public class AdminController {
    private AdminView view;

    public AdminController(AdminView view) {
        this.view = view;

        // Event Listeners for Button Actions
        view.addManageStudentsListener(e -> manageStudents());
        view.addManageTeachersListener(e -> manageTeachers());
        view.addManageClassesListener(e -> manageClasses());
        view.addManageSubjectListener(e -> manageSubjects());
        view.addGenerateReportListener(e -> generateReport());
        view.addLogoutListener(e -> logout());
    }

    private void manageStudents() {
        ManageStudents manageStudentView = new ManageStudents();
        new StudentManagementController(manageStudentView);
        manageStudentView.setLocationRelativeTo(null);
        manageStudentView.setVisible(true);

        // Add a window listener to detect when ManageClasses is closed
        manageStudentView.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Reopen the main view
                view.setLocationRelativeTo(null);
                view.setVisible(true);
            }
        });

        // Dispose the current view
        view.dispose();
    }

    private void manageTeachers() {
        ManageTeachers manageTeacherView = new ManageTeachers();
        new TeacherManagementController(manageTeacherView);
        manageTeacherView.setLocationRelativeTo(null);
        manageTeacherView.setVisible(true);

        // Add a window listener to detect when ManageClasses is closed
        manageTeacherView.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Reopen the main view
                view.setLocationRelativeTo(null);
                view.setVisible(true);
            }
        });

        // Dispose the current view
        view.dispose();
    }

    private void manageClasses() {
        ManageClasses classView = new ManageClasses();
        new ClassManagementController(classView);
        classView.setLocationRelativeTo(null);
        classView.setVisible(true);

        // Add a window listener to detect when ManageClasses is closed
        classView.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Reopen the main view
                view.setLocationRelativeTo(null);
                view.setVisible(true);
            }
        });

        // Dispose the current view
        view.dispose();
    }
    
    private void manageSubjects() {
        ManageSubjects subjectman = new ManageSubjects();
        new SubjectManagementController(subjectman);
        subjectman.setLocationRelativeTo(null);
        subjectman.setVisible(true);

        // Add a window listener to detect when ManageClasses is closed
        subjectman.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Reopen the main view
                view.setLocationRelativeTo(null);
                view.setVisible(true);
            }
        });

        // Dispose the current view
        view.dispose();
    }

    private void generateReport() {
        JOptionPane.showMessageDialog(view, "Generating Report...");
    }

    private void logout() {
        view.dispose();
        LoginView lview = new LoginView();
        lview.setLocationRelativeTo(null);
        lview.setVisible(true);
    }
}
