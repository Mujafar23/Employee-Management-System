package com.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/employee_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Mujafar7035";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<String[]> getAllEmployees() {
        List<String[]> employees = new ArrayList<>();
        String query = "SELECT * FROM employees ORDER BY id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                employees.add(new String[]{
                    String.valueOf(rs.getInt("id")),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("department"),
                    rs.getString("mobile"),
                    rs.getString("address")
                });
            }
        } catch (SQLException e) {
            showError("Error loading employees: " + e.getMessage());
        }
        return employees;
    }

    public boolean employeeExists(int id) {
        String query = "SELECT id FROM employees WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            showError("Error checking employee existence: " + e.getMessage());
            return false;
        }
    }

    public boolean addEmployee(int id, String name, String email, String department, String mobile_no, String address) {
        if (employeeExists(id)) {
            showError("Employee ID " + id + " already exists!");
            return false;
        }

        String query = "INSERT INTO employees VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, department);
            stmt.setString(5, mobile_no);
            stmt.setString(6, address);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            showError("Error adding employee: " + e.getMessage());
            return false;
        }
    }

    public boolean updateEmployee(int id, String name, String email, String department, String mobile_no, String address) {
        String query = "UPDATE employees SET name=?, email=?, department=?, mobile=?, address=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, department);
            stmt.setString(4, mobile_no);
            stmt.setString(5, address);
            stmt.setInt(6, id);

            int result = stmt.executeUpdate();
            if (result == 0) {
                showError("No employee found with ID: " + id);
                return false;
            }
            return true;
        } catch (SQLException e) {
            showError("Error updating employee: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteEmployee(int id) {
        String query = "DELETE FROM employees WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            if (result == 0) {
                showError("No employee found with ID: " + id);
                return false;
            }
            return true;
        } catch (SQLException e) {
            showError("Error deleting employee: " + e.getMessage());
            return false;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}