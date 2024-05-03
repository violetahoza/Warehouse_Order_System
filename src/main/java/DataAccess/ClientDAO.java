package DataAccess;

import Model.Client;

/**
 * Data Access Object (DAO) for performing CRUD operations on Client objects.
 * Extends the AbstractDAO class, providing generic CRUD functionalities.
 */
public class ClientDAO extends AbstractDAO<Client>{
    public ClientDAO(Class<Client> type) {
        super(type);
    }
}
