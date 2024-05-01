package org.example.dataAccess;

import java.util.logging.Logger;
import org.example.model.Client;

public class ClientDAO extends AbstractDAO<Client>{

    public Client insertClient(Client client) {
        return super.insert(client);
    }

    public Client updateClient(Client client) {
        return super.update(client);
    }
}
