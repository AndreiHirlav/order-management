package org.example.presentation;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class View extends JFrame{
    public View() {
        setTitle("Order Management");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        init();
    }

    private void init() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Clients", createClientPanel());
        tabbedPane.addTab("Products", createProductPanel());
        tabbedPane.addTab("Orders", createOrderPanel());

        add(tabbedPane);
    }

    private JPanel createClientPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"idClient", "NumeClient", "Email"};
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("Add Client"));
        buttonPanel.add(new JButton("Edit Client"));
        buttonPanel.add(new JButton("Delete Client"));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"idProduct", "Nume", "Cantitate", "Pret"};
        Object[][] data = {};  // Replace with actual data retrieval logic
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("Add Product"));
        buttonPanel.add(new JButton("Edit Product"));
        buttonPanel.add(new JButton("Delete Product"));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JComboBox<String> clientComboBox = new JComboBox<>(new String[]{"Client 1", "Client 2", "Client 3"});
        JComboBox<String> productComboBox = new JComboBox<>(new String[]{"Product 1", "Product 2", "Product 3"});
        JTextField quantityField = new JTextField();
        JButton submitButton = new JButton("Place Order");

        panel.add(new JLabel("Select Client:"));
        panel.add(clientComboBox);
        panel.add(new JLabel("Select Product:"));
        panel.add(productComboBox);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(new JLabel(""));
        panel.add(submitButton);

        return panel;
    }
}
