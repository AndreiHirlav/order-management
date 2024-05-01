package org.example.model;

public class Order {
    private int idOrder;
    private int idClient;
    private int idProduct;
    private int TotalPrice;

    public Order(int idOrder, int idClient, int idProduct, int totalPrice) {
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.idProduct = idProduct;
        TotalPrice = totalPrice;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }
}
