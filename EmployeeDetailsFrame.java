package com.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EmployeeDetailsFrame extends JFrame {
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private DatabaseHelper dbHelper;

    public EmployeeDetailsFrame() {
        setTitle("Employee Details");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        dbHelper = new DatabaseHelper();
        initUI();
        loadEmployeeData();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Table setup
        String[] columns = {"ID", "Name", "Email", "Department", "Mobile No", "Address"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Double-click listener
        employeeTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showEmployeeDetails();
                }
            }
        });

        mainPanel.add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        
        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadEmployeeData());
        mainPanel.add(refreshButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadEmployeeData() {
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0);
            dbHelper.getAllEmployees().forEach(tableModel::addRow);
        });
    }

    private void showEmployeeDetails() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) return;

        String[] employee = new String[6];
        for (int i = 0; i < 6; i++) {
            employee[i] = tableModel.getValueAt(selectedRow, i).toString();
        }

        String details = String.format(
            "<html><b>ID:</b> %s<br>"
            + "<b>Name:</b> %s<br>"
            + "<b>Email:</b> %s<br>"
            + "<b>Department:</b> %s<br>"
            + "<b>Mobile No:</b> %s<br>"
            + "<b>Address:</b> %s</html>",
            employee[0], employee[1], employee[2], employee[3], employee[4], employee[5]
        );

        JOptionPane.showMessageDialog(this, details, "Employee Details", JOptionPane.INFORMATION_MESSAGE);
    }
}