package org.example.presentation;

import org.example.dataAccess.ClientDAO;
import org.example.dataAccess.ProductDAO;
import org.example.model.Client;
import org.example.model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerMethods {
    public void addClientDialog(JFrame frame) {
        final JDialog dialog = new JDialog(frame, "Add Client", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);

        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        final JTextField nameField = new JTextField(20);
        final JTextField emailField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client = new Client();
                client.setNumeClient(nameField.getText());
                client.setEmail(emailField.getText());
                ClientDAO clientDAO = new ClientDAO();
                clientDAO.insert(client);
                dialog.dispose();
            }
        });

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(submitButton, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public void addProductDialog(JFrame frame) {
        final JDialog dialog = new JDialog(frame, "Add Product", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);

        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        final JTextField nameField = new JTextField(20);
        final JTextField priceField = new JTextField(20);
        final JTextField quantityField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product product = new Product();
                product.setNume(nameField.getText());
                product.setPret(Integer.parseInt(priceField.getText()));
                product.setCantitate(Integer.parseInt(quantityField.getText()));
                ProductDAO productDAO = new ProductDAO();
                productDAO.insert(product);
                dialog.dispose();
            }
        });

        formPanel.add(new JLabel("Product Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Price of Product:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Quantity of Product:"));
        formPanel.add(quantityField);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(submitButton, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public void editClientDialog(JFrame frame, JTable table) {
        if(table.getSelectedRow() != -1) {
            final JDialog dialog = new JDialog(frame, "Edit Client", true);
            dialog.setLayout(new GridLayout(0, 2));

            int row = table.getSelectedRow();
            final String id = table.getValueAt(row, 0).toString();
            final String name = table.getValueAt(row, 1).toString();
            final String email = table.getValueAt(row, 2).toString();

            final JTextField nameField = new JTextField(name);
            final JTextField emailField = new JTextField(email);
            JButton submitButton = new JButton("Update");

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Client client = new Client();
                    client.setId(Integer.parseInt(id));
                    client.setNumeClient(nameField.getText());
                    client.setEmail(emailField.getText());
                    ClientDAO clientDAO = new ClientDAO();
                    clientDAO.update(client);
                    dialog.dispose();
                }
            });

            dialog.add(new JLabel("New Name:"));
            dialog.add(nameField);
            dialog.add(new JLabel("New Email:"));
            dialog.add(emailField);
            dialog.add(submitButton);

            dialog.pack();
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a client to edit");
        }
    }

    public void editProductDialog(JFrame frame, JTable table) {
        if(table.getSelectedRow() != -1) {
            final JDialog dialog = new JDialog(frame, "Edit Product", true);
            dialog.setLayout(new GridLayout(0, 2));

            int row = table.getSelectedRow();
            final String id = table.getValueAt(row, 0).toString();
            final String name = table.getValueAt(row, 1).toString();
            final String quantity = table.getValueAt(row, 2).toString();
            final String price = table.getValueAt(row, 3).toString();

            final JTextField nameField = new JTextField(name);
            final JTextField quantityField = new JTextField(quantity);
            final JTextField priceField = new JTextField(price);
            JButton submitButton = new JButton("Update");

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Product product = new Product();
                    product.setId(Integer.parseInt(id));
                    product.setNume(nameField.getText());
                    product.setCantitate(Integer.parseInt(quantityField.getText()));
                    product.setPret(Integer.parseInt(priceField.getText()));
                    ProductDAO productDAO = new ProductDAO();
                    productDAO.update(product);
                    dialog.dispose();
                }
            });

            dialog.add(new JLabel("New Product Name:"));
            dialog.add(nameField);
            dialog.add(new JLabel("New Quantity:"));
            dialog.add(quantityField);
            dialog.add(new JLabel("New Price:"));
            dialog.add(priceField);
            dialog.add(submitButton);

            dialog.pack();
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a product to edit");
        }
    }

    public void deleteClientDialog(JFrame frame, JTable table) {
        if (table.getSelectedRow() != -1) {
            int row = table.getSelectedRow();
            int clientId = Integer.parseInt(table.getValueAt(row, 0).toString());
            ClientDAO clientDAO = new ClientDAO();

            boolean deleted = clientDAO.delete(new Client(clientId));
            if (deleted) {
                JOptionPane.showMessageDialog(frame, "Client deleted successfully");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to delete client");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a client to delete");
        }
    }

    public void deleteProductDialog(JFrame frame, JTable table) {
        if (table.getSelectedRow() != -1) {
            int row = table.getSelectedRow();
            int productId = Integer.parseInt(table.getValueAt(row, 0).toString());
            ProductDAO productDAO = new ProductDAO();

            boolean deleted = productDAO.delete(new Product(productId));
            if (deleted) {
                JOptionPane.showMessageDialog(frame, "Product deleted successfully");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to delete product");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a product to delete");
        }
    }
}
