# CampusIssueSystem

**A Console-Based Campus Issue Reporting and Management System**

CampusIssueSystem is a Java application designed to help students report campus problems and allow administrators to assign workpersons to resolve them. The system uses Object-Oriented Programming (OOP), JDBC, and MySQL for database management.

## Features

### Student Module

* Student registration and login.
* Registration restricted to official college email addresses ending with `@rajalakshmi.edu.in`.
* Student ID validation: exactly 9 digits.
* Report campus problems by selecting a category.
* Enter the problem description and location.
* View submitted reports and their status.

### Admin Module

* Secure admin login using predefined credentials.
* View reports submitted by students.
* Assign workpersons to reported issues.
* Update and monitor issue status.

### Technical Features

* Java console-based interface.
* MySQL database integration using JDBC.
* Maven dependency management.
* Object-Oriented Programming principles.
* Data Access Object (DAO) pattern.
* Input validation and exception handling.
* Prepared statements for safer SQL queries.

## Technology Stack

| Technology | Purpose                         |
| ---------- | ------------------------------- |
| Java       | Application logic               |
| MySQL      | Relational database             |
| JDBC       | Java–MySQL connectivity         |
| Maven      | Build and dependency management |
| VS Code    | Development environment         |

## Project Structure

```text
CampusIssueSystem/
├── pom.xml
├── README.md
├── database/
│   ├── schema.sql
│   └── seed.sql
└── src/
    └── main/
        └── java/
            └── com/
                └── campusissue/
                    ├── Main.java
                    ├── model/
                    ├── service/
                    ├── dao/
                    ├── database/
                    └── util/
```

## Prerequisites

Install the following before running the application:

1. JDK 17 or a compatible newer JDK.
2. MySQL Server.
3. Apache Maven.
4. Visual Studio Code with the Java Extension Pack.

## Database Setup

1. Start your MySQL server.
2. Open MySQL Workbench or the MySQL command-line client.
3. Execute the SQL file `database/schema.sql` to create the database and tables.
4. Execute `database/seed.sql` if it contains initial data, such as admin accounts or workpersons.

Example:

```sql
SOURCE /absolute/path/to/CampusIssueSystem/database/schema.sql;
SOURCE /absolute/path/to/CampusIssueSystem/database/seed.sql;
```

Replace the example paths with the actual paths on your computer.

## Configure Database Connection

Update the database connection settings in:

`src/main/java/com/campusissue/database/DatabaseConnection.java`

Configure the following values for your local MySQL installation:

* Database URL
* MySQL username
* MySQL password

Do not commit real database passwords to a public GitHub repository. Use environment variables or an external configuration file for credentials.

## Build and Run

Open a terminal in the project root directory.

Compile the project:

```bash
mvn clean compile
```

Run the application using the Maven Exec plugin:

```bash
mvn exec:java
```

The `Main.java` file should contain the application's entry point and console menu.

## Application Workflow

1. Launch the application.
2. Select Student Login, Student Registration, or Admin Login.
3. Register or authenticate using the appropriate credentials.
4. Students select an issue category, enter a description, and provide the location.
5. Administrators review submitted issues and assign workpersons.
6. Reports can be tracked as their status changes.

## OOP Concepts Used

* **Encapsulation:** Private fields with controlled access through methods.
* **Inheritance:** Student and Admin can extend a common User class.
* **Abstraction:** Services and model classes separate application responsibilities.
* **Polymorphism:** Overridden methods can provide role-specific behavior.

## Architecture

* **Model:** Represents users, problems, reports, and workpersons.
* **DAO:** Performs database operations.
* **Service:** Implements application business rules.
* **Database:** Manages MySQL connections.
* **Util:** Contains reusable validation logic.
* **Main:** Starts the application and manages console navigation.

## Future Enhancements

* Report priority and deadline management.
* Report status history.
* Password hashing.
* Search and filtering for administrators.
* Resolution notes and completion timestamps.
* Automated notifications when reports are assigned or resolved.

## Project Objective

The objective of CampusIssueSystem is to demonstrate practical Java OOP, JDBC connectivity, SQL database design, validation, and layered application architecture through a real-world campus issue management scenario.
