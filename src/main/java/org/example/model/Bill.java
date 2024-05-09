package org.example.model;

public record Bill(int idClient, int idProdus, int cantitate, int pretTotal) {
    @Override
    public int idClient() {
        return idClient;
    }

    @Override
    public int idProdus() {
        return idProdus;
    }

    @Override
    public int cantitate() {
        return cantitate;
    }

    @Override
    public int pretTotal() {
        return pretTotal;
    }
}
