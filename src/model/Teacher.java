/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author gh
 */
public class Teacher {
    private int id;
    private String name;
    private String email;
    private String subject;

    public Teacher(int id, String name, String email, String subject) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.subject = subject;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getSubject() { return subject; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setSubject(String subject) { this.subject = subject; }
}

