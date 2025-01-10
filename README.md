# Attendance and Payments Management System

This is a Java-based LMS built using the **MVC (Model-View-Controller)** architecture. The application provides functionality to manage attendance, payments, teachers, classes, and enrollments. It features a modern UI using the **FlatLaf** theme and generates detailed reports using **JasperReports**.

## Features

### 1. **Mark Attendance**
- Record attendance for students or teachers.
- View attendance history by date or individual.
- Export attendance reports using JasperReports.

### 2. **Payments Management**
- Manage payments for students and teachers.
- Generate payment receipts.
- View payment history.

### 3. **Teacher Management**
- Add, update, or delete teacher records.
- Assign teachers to specific classes.
- Generate teacher-related reports.

### 4. **Class Management**
- Create and manage classes.
- Assign students and teachers to classes.
- View class schedules.

### 5. **Enrollment Management**
- Enroll students in classes.
- View enrollment details.
- Generate enrollment reports.

---

## Project Structure

The application follows the **MVC (Model-View-Controller)** design pattern:

- **Model**: Handles data logic and database operations.
- **View**: Renders the user interface using Swing and FlatLaf.
- **Controller**: Manages interactions between the Model and View.

---

## Setup Instructions

### Prerequisites

1. **Java Development Kit (JDK)**:
   - Version: `17` or higher
   - Download: [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) or [OpenJDK](https://openjdk.java.net/)

2. **JasperReports Library**:
   - Version: `6.20.0`
   - Add to your project via Maven or manually include the JAR files.

3. **FlatLaf Theme**:
   - Version: `3.0`
   - Include the FlatLaf JAR file for modern UI design.

4. **Database**:
   - MySQL `8.0` or higher
   - Use the MySQL Connector/J driver (`mysql-connector-java-8.x.x.jar`).

5. **Build Tool**:
   - Maven or manually handle dependencies.

---

### Installation Steps

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/NIBM-EAD-LMS.git
   cd NIBM-EAD-LMS
   ```

2. **Setup Database**:
   - Create a MySQL database.
   - Run the provided `schema.sql` script to create the necessary tables.
   - Update the `Connection` with your MySQL credentials, following are defaults:
     ```java
     String url = "jdbc:mysql://localhost/lms";
     String user = "root";
     String password = "";
     ```

3. **Add Dependencies**:
   - Add the following libraries to your project:
     - `jasperreports-6.20.0.jar`
     - `flatlaf-3.0.jar`
     - `mysql-connector-java-8.x.x.jar`
   - If using Maven, include these dependencies in `pom.xml`:
     ```xml
     <dependencies>
         <dependency>
             <groupId>net.sf.jasperreports</groupId>
             <artifactId>jasperreports</artifactId>
             <version>6.20.0</version>
         </dependency>
         <dependency>
             <groupId>com.formdev</groupId>
             <artifactId>flatlaf</artifactId>
             <version>3.0</version>
         </dependency>
         <dependency>
             <groupId>mysql</groupId>
             <artifactId>mysql-connector-java</artifactId>
             <version>8.0.x</version>
         </dependency>
     </dependencies>
     ```

4. **Compile and Run**:
   ```bash
   mvn clean install
   java -jar dist/lms.jar
   ```

---

## Usage

### Attendance Management
1. Navigate to the **Attendance** section.
2. Select the class and date.
3. Mark attendance for individuals.
4. Export attendance reports via the **Generate Report** button.

### Payments Management
1. Open the **Payments** section.
2. Record payments for students or teachers.
3. View payment history and generate receipts.

### Teacher Management
1. Add new teachers using the **Add Teacher** form.
2. Assign teachers to specific classes.
3. View and edit teacher information.

### Class Management
1. Create new classes with relevant details.
2. Assign students and teachers to classes.
3. View schedules and class rosters.

### Enrollment Management
1. Enroll students in classes via the **Enroll Student** form.
2. View enrollment details.
3. Generate enrollment reports.

---

## Screenshots

_Add relevant screenshots of the application here._

---

## Troubleshooting

1. **JDBC Connection Issues**:
   - Verify your database URL, username, and password.
   - Ensure MySQL is running and accessible.

2. **Missing Libraries**:
   - Ensure all required JAR files are in the classpath.
   - Rebuild the project if necessary.

3. **UI Issues**:
   - Ensure the FlatLaf library is correctly loaded.
   - Use the latest Java version for better compatibility.

---

## Acknowledgments

- **JasperReports** for robust reporting capabilities.
- **FlatLaf** for modern and stylish UI components.
- **MySQL** for efficient database management.

---
