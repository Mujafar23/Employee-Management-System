package com.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class Home extends JFrame {
    private JTextField idField, nameField, emailField, departmentField, mobileField, addressField;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private DatabaseHelper dbHelper;

    public Home() {
        setTitle("Employee Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        dbHelper = new DatabaseHelper();
        setVisible(true);
        initUI();
        loadTableData();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        idField = createFormField(formPanel, "Employee ID:");
        nameField = createFormField(formPanel, "Name:");
        emailField = createFormField(formPanel, "Email:");
        departmentField = createFormField(formPanel, "Department:");
        mobileField = createFormField(formPanel, "Mobile No:");
        addressField = createFormField(formPanel, "Address:");
        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Name", "Email", "Department", "Mobile No", "Address"};
        tableModel = new DefaultTableModel(columns, 0);
        employeeTable = new JTable(tableModel);
        employeeTable.getSelectionModel().addListSelectionListener(e -> populateFormFromTable());
        tablePanel.add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(createActionButton("Add", this::addEmployee));
        buttonPanel.add(createActionButton("Update", this::updateEmployee));
        buttonPanel.add(createActionButton("Delete", this::deleteEmployee));
        buttonPanel.add(createActionButton("Clear", e -> clearForm()));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        JButton backButton = createActionButton("Back", e -> {
            new NavigationFrame(); // Open Navigation Frame
            dispose(); // Close Home Frame
        });
        buttonPanel.add(backButton);

        add(mainPanel);
    }

    private JTextField createFormField(JPanel panel, String label) {
        panel.add(new JLabel(label));
        JTextField textField = new JTextField();
        panel.add(textField);
        return textField;
    }

    private JButton createActionButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 30));
        button.addActionListener(action);
        return button;
    }

    private boolean validateInputs() {
        if (idField.getText().trim().isEmpty() ||
            nameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() ||
            departmentField.getText().trim().isEmpty() ||
            mobileField.getText().trim().isEmpty() ||
            addressField.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            Integer.parseInt(idField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Employee ID format!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }

    private void addEmployee(ActionEvent e) {
        if (!validateInputs()) return;
        
        try {
            int id = Integer.parseInt(idField.getText());
            if (dbHelper.addEmployee(id, nameField.getText(), emailField.getText(),
                    departmentField.getText(), mobileField.getText(), addressField.getText())) {
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
                loadTableData();
                clearForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmployee(ActionEvent e) {
        if (!validateInputs()) return;
        
        try {
            int id = Integer.parseInt(idField.getText());
            if (dbHelper.updateEmployee(id, nameField.getText(), emailField.getText(),
                    departmentField.getText(), mobileField.getText(), addressField.getText())) {
                JOptionPane.showMessageDialog(this, "Employee updated successfully!");
                loadTableData();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee(ActionEvent e) {
        if (idField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Employee ID to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int id = Integer.parseInt(idField.getText());
            if (dbHelper.deleteEmployee(id)) {
                JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
                loadTableData();
                clearForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateFormFromTable() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) return;

        idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
        nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
        emailField.setText(tableModel.getValueAt(selectedRow, 2).toString());
        departmentField.setText(tableModel.getValueAt(selectedRow, 3).toString());
        mobileField.setText(tableModel.getValueAt(selectedRow, 4).toString());
        addressField.setText(tableModel.getValueAt(selectedRow, 5).toString());
    }

    private void loadTableData() {
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0);
            dbHelper.getAllEmployees().forEach(tableModel::addRow);
        });
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        departmentField.setText("");
        mobileField.setText("");
        addressField.setText("");
        employeeTable.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Home::new);
    }
}