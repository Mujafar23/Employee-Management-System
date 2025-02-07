# Employee Management System

## Overview
The **Employee Management System** is a Java-based desktop application developed using **Swing** for the graphical user interface. The system allows users to perform CRUD (Create, Read, Update, Delete) operations on employee records, making it a useful tool for managing employee data efficiently.

## Features
- Add new employees
- View employee details
- Update employee records
- Delete employee records
- User-friendly interface with Swing components
- Proper validation and error handling

## Technologies Used
- **Java** (JDK 8 or later)
- **Swing** (for UI development)
- **JDBC** (for database connectivity)
- **MySQL** or any relational database
- **Eclipse/IntelliJ IDEA** (Recommended IDE)

## Installation & Setup
### Prerequisites
- Install **JDK 8 or later**
- Install **MySQL Server** (or any other RDBMS)
- Set up **JDBC Driver** for database connection

### Steps to Run the Project
1. **Clone the Repository**
   ```sh
   git clone <repository-url>
   cd employee-management-system
   ```

2. **Set Up the Database**
   - Create a database in MySQL:
     ```sql
     CREATE DATABASE employee_db;
     ```
   - Import the provided SQL file (`employee_db.sql`) to set up the required tables.

3. **Configure Database Connection**
   - Update database credentials in the project’s configuration file:
     ```java
     String url = "jdbc:mysql://localhost:3306/employee_db";
     String user = "your_username";
     String password = "your_password";
     ```

4. **Compile and Run the Application**
   - If using **Eclipse/IntelliJ**, import the project and run the `Main.java` file.
   - If using the command line:
     ```sh
     javac -cp .:mysql-connector-java.jar Main.java
     java -cp .:mysql-connector-java.jar Main
     ```

## Screenshots
![image](https://github.com/user-attachments/assets/b1078669-6ba3-499b-8866-09010442cc3b)
![image](https://github.com/user-attachments/assets/a2ac8787-08ee-4d12-a92f-0661a9ad9d84)
![image](https://github.com/user-attachments/assets/0eccfae9-14e2-4ad2-9b9e-b621bc20489f)


## Future Improvements
- Implement search functionality
- Enhance UI with better design
- Add user authentication

## License
This project is open-source and available under the **MIT License**.

## Contact
For any queries, feel free to reach out:
- Email: mujafar7035@gmail.com
- GitHub: [Your GitHub Profile](https://github.com/Mujafar23)

