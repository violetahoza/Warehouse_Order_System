package DataAccess;

import Model.Client;

public class ClientDAO extends AbstractDAO<Client>{
    public ClientDAO(Class<Client> type) {
        super(type);
    }
}
