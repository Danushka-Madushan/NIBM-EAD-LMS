package model;

/**
 *
 * @author gh
 */

public class Student {
    private int id;
    private String name;
    private String email;
    private int classId;
    private String className;  // Optional for display purposes

    // Constructor for Full Data
    public Student(int id, String name, String email, int classId, String className) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.classId = classId;
        this.className = className;
    }

    // Constructor without Class Name
    public Student(int id, String name, String email, int classId) {
        this(id, name, email, classId, null);
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getClassId() { return classId; }
    public String getClassName() { return className; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setClassId(int classId) { this.classId = classId; }
    public void setClassName(String className) { this.className = className; }
}
