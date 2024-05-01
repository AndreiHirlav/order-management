package org.example.model;

public class Client {
    private int idClient;
    private String numeClient;
    private String email;

    public Client(int idClient, String numeClient, String email) {
        this.idClient = idClient;
        this.numeClient = numeClient;
        this.email = email;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
