package org.example.presentation;
import org.example.dataAccess.AbstractDAO;
import org.example.dataAccess.ClientDAO;
import org.example.dataAccess.ProductDAO;
import org.example.model.Client;
import org.example.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class View extends JFrame{
    public View() {
        setTitle("Order Management");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        setVisible(true);
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
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clients = clientDAO.findAll();
        try {
            JTable table = new JTable(AbstractDAO.buildTable(clients));
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane, BorderLayout.CENTER);
        } catch( IllegalAccessException e ) {
            e.printStackTrace();
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("Add Client"));
        buttonPanel.add(new JButton("Edit Client"));
        buttonPanel.add(new JButton("Delete Client"));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.findAll();
        try {
            JTable table = new JTable(AbstractDAO.buildTable(products));
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane, BorderLayout.CENTER);
        } catch( IllegalAccessException e ) {
            e.printStackTrace();
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("Add Product"));
        buttonPanel.add(new JButton("Edit Product"));
        buttonPanel.add(new JButton("Delete Product"));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        ClientDAO clientDAO = new ClientDAO();
        List<Client> clients = clientDAO.findAll();
        final JComboBox<Client> clientComboBox = new JComboBox<>(clients.toArray(new Client[0]));

        final ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.findAll();
        final JComboBox<Product> productComboBox = new JComboBox<>(products.toArray(new Product[0]));

        final JTextField quantityField = new JTextField();
        JButton submitButton = new JButton("Place Order");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleOrder(clientComboBox, productComboBox, quantityField);
            }
        });
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

    private void handleOrder(JComboBox<Client> clientComboBox, JComboBox<Product> productComboBox, JTextField quantityField) {
        Client selectedClient = (Client) clientComboBox.getSelectedItem();
        Product selectedProduct = (Product) productComboBox.getSelectedItem();
        int quantity = Integer.parseInt(quantityField.getText());

        if(selectedProduct.getCantitate() < quantity) {
            JOptionPane.showMessageDialog(this, "Not enough stock available.", "Under-Stock", JOptionPane.ERROR_MESSAGE);
        } else {
            selectedProduct.setCantitate(selectedProduct.getCantitate() - quantity);
            ProductDAO productDAO = new ProductDAO();
            productDAO.updateQuantity(selectedProduct.getId(), quantity);
            JOptionPane.showMessageDialog(this, "Order placed successfully!", "Order Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
