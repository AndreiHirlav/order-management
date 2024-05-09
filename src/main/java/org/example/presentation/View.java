package org.example.presentation;
import org.example.dataAccess.AbstractDAO;
import org.example.dataAccess.BillDAO;
import org.example.dataAccess.ClientDAO;
import org.example.dataAccess.ProductDAO;
import org.example.model.Bill;
import org.example.model.Client;
import org.example.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class View extends JFrame{
    ListenerMethods methods = new ListenerMethods();
    private JTable clientTable;
    private JTable productTable;
    private JTable billTable;
    JComboBox<Client> clientComboBox;
    JComboBox<Product> productComboBox;
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
        tabbedPane.addTab("Log", createBillPanel());

        add(tabbedPane);
    }

    public void refreshTables() {
        refreshClientTable();
        refreshProductTable();
    }

    private void refreshClientTable() {
        DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
        model.setRowCount(0);
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clients = clientDAO.findAll();

        try {
            for(Client client : clients) {
                model.addRow(new Object[]{client.getId(), client.getNumeClient(), client.getEmail()});
            }
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
    private void refreshClientBox(JComboBox<Client> clientComboBox) {
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clients = clientDAO.findAll();
        clientComboBox.removeAllItems();
        for(Client client : clients) {
            clientComboBox.addItem(client);
        }
    }

    private void refreshProductBox(JComboBox<Product> productComboBox) {
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.findAll();
        productComboBox.removeAllItems();
        for(Product product : products) {
            productComboBox.addItem(product);
        }
    }

    private void refreshProductTable() {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.findAll();

        try {
            for(Product product : products) {
                model.addRow(new Object[]{product.getId(), product.getNume(), product.getCantitate(), product.getPret()});
            }
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    private void refreshBillTable() {
        DefaultTableModel model = (DefaultTableModel) billTable.getModel();
        model.setRowCount(0);
        List<Bill> bills = BillDAO.readBills();

        try {
            for(Bill bill : bills) {
                model.addRow(new Object[]{bill.idClient(), bill.idProdus(), bill.cantitate(), bill.pretTotal()});
            }
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    private JPanel createClientPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clients = clientDAO.findAll();
        try {
            clientTable = new JTable(AbstractDAO.buildTable(clients));
            JScrollPane scrollPane = new JScrollPane(clientTable);
            panel.add(scrollPane, BorderLayout.CENTER);
        } catch( IllegalAccessException e ) {
            e.printStackTrace();
        }

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Client");
        buttonPanel.add(addButton);
        JButton editButton = new JButton("Edit Client");
        buttonPanel.add(editButton);
        JButton deleteButton = new JButton("Delete Client");
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                methods.addClientDialog(View.this);
                refreshTables();
                refreshClientBox(clientComboBox);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                methods.editClientDialog(View.this, clientTable);
                refreshTables();
                refreshClientBox(clientComboBox);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                methods.deleteClientDialog(View.this, clientTable);
                refreshTables();
                refreshClientBox(clientComboBox);
            }
        });

        return panel;
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.findAll();
        try {
            productTable = new JTable(AbstractDAO.buildTable(products));
            JScrollPane scrollPane = new JScrollPane(productTable);
            panel.add(scrollPane, BorderLayout.CENTER);
        } catch( IllegalAccessException e ) {
            e.printStackTrace();
        }

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Product");
        buttonPanel.add(addButton);
        JButton editButton = new JButton("Edit Product");
        buttonPanel.add(editButton);
        JButton deleteButton = new JButton("Delete ");
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                methods.addProductDialog(View.this);
                refreshTables();
                refreshProductBox(productComboBox);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                methods.editProductDialog(View.this, productTable);
                refreshTables();
                refreshProductBox(productComboBox);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                methods.deleteProductDialog(View.this, productTable);
                refreshTables();
                refreshProductBox(productComboBox);
            }
        });

        return panel;
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        ClientDAO clientDAO = new ClientDAO();
        List<Client> clients = clientDAO.findAll();
        clientComboBox = new JComboBox<>(clients.toArray(new Client[0]));

        final ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.findAll();
        productComboBox = new JComboBox<>(products.toArray(new Product[0]));

        final JTextField quantityField = new JTextField();
        JButton submitButton = new JButton("Place Order");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleOrder(quantityField);
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

    private void handleOrder(JTextField quantityField) {
        Client selectedClient = (Client) clientComboBox.getSelectedItem();
        Product selectedProduct = (Product) productComboBox.getSelectedItem();
        int quantity = Integer.parseInt(quantityField.getText());

        if(selectedProduct.getCantitate() < quantity) {
            JOptionPane.showMessageDialog(this, "Not enough stock available.", "Under-Stock", JOptionPane.ERROR_MESSAGE);
        } else {
            BillDAO billDAO = new BillDAO();
            billDAO.insertBill(new Bill(selectedClient.getId(), selectedProduct.getId(), quantity, quantity * selectedProduct.getPret()));

            selectedProduct.setCantitate(selectedProduct.getCantitate() - quantity);
            ProductDAO productDAO = new ProductDAO();
            productDAO.updateQuantity(selectedProduct.getId(), quantity);
            JOptionPane.showMessageDialog(this, "Order placed successfully!", "Order Success", JOptionPane.INFORMATION_MESSAGE);
            refreshTables();
            refreshProductBox(productComboBox);
            refreshBillTable();

        }
    }

    private JPanel createBillPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        BillDAO billDAO = new BillDAO();
        List<Bill> bills = BillDAO.readBills();
        try {
            billTable = new JTable(AbstractDAO.buildTable(bills));
            JScrollPane scrollPane = new JScrollPane(billTable);
            panel.add(scrollPane, BorderLayout.CENTER);
        } catch( IllegalAccessException e ) {
            e.printStackTrace();
        }

        return panel;
    }
}
