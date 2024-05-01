package org.example.model;

public class Product {
    private int idProduct;
    private String nume;
    private int cantitate;
    private int pret;

    public Product(int idProduct, String nume, int cantitate, int pret) {
        this.idProduct = idProduct;
        this.nume = nume;
        this.cantitate = cantitate;
        this.pret = pret;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }
}
