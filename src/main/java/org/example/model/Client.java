package org.example.model;

public class Client {
    private int id;
    private String numeClient;
    private String email;

    public Client(int idClient, String numeClient, String email) {
        this.id = idClient;
        this.numeClient = numeClient;
        this.email = email;
    }

    public Client() {

    }

    public Client(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int idClient) {
        this.id = idClient;
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
    @Override
    public String toString() {
        return numeClient;
    }


}
