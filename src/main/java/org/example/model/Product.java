package org.example.model;

public class Product {
    private int id;
    private String nume;
    private int cantitate;
    private int pret;

    public Product(int idProduct, String nume, int cantitate, int pret) {
        this.id = idProduct;
        this.nume = nume;
        this.cantitate = cantitate;
        this.pret = pret;
    }
    public Product() {

    }

    public Product(int productId) {
        this.id = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int idProduct) {
        this.id = idProduct;
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

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return nume + " (pe stoc: " + cantitate + ")";
    }
}
