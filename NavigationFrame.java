package com.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationFrame extends JFrame {

    public NavigationFrame() {
        setTitle("Employee Management System");
        setSize(727, 436);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.add(createLabel("ITINFO ACADEMY", Font.BOLD, 18));
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.add(createButton("ADD / UPDATE / DELETE", e -> new Home()));
        centerPanel.add(createButton("Employee Details", e -> new EmployeeDetailsFrame().setVisible(true)));
        centerPanel.add(createButton("Exit", e -> System.exit(0)));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.add(createLabel("Employee Management System", Font.PLAIN, 16));
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.PLAIN, 16));
        button.setForeground(new Color(0, 128, 255));
        button.setPreferredSize(new Dimension(300, 60));
        button.addActionListener(listener);
        return button;
    }

    private JLabel createLabel(String text, int style, int size) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Tahoma", style, size));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NavigationFrame::new);
    }
}