package controller;

import java.sql.*;
import java.util.HashMap;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gh
 */
public class ReportController {
    private static final String URL = "jdbc:mysql://localhost/lms";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void generateStudentReport() {
        try {
            // Establish DB Connection
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Load the Jasper Report
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/student_report.jrxml");
            
            // Fill Report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conn);
            
            // Create the JasperViewer instance
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            
            // Force the report window to appear on top
            viewer.setAlwaysOnTop(true);
            viewer.setVisible(true);
            
            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void generatePaymentReport() {
        try {
            // Establish DB Connection
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Load the Jasper Report
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/payment_report.jrxml");
            
            // Fill Report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conn);
            
            // Create the JasperViewer instance
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            
            // Force the report window to appear on top
            viewer.setAlwaysOnTop(true);
            viewer.setVisible(true);
            
            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
