package BusinessLogic;

import BusinessLogic.Validators.ClientValidator;
import BusinessLogic.Validators.EmailValidator;
import DataAccess.AbstractDAO;
import DataAccess.ClientDAO;
import Model.Client;
import Presentation.AddController;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Business Logic Layer (BLL) class for performing operations on Client objects.
 * Acts as an intermediary between the Presentation layer and DataAccess layer.
 */
public class ClientBLL {
    /** The data access object for interacting with Client objects in the database. */
    ClientDAO clientDAO = new ClientDAO(Client.class);
    public ClientBLL(){}

    /**
     * Retrieves all clients from the database.
     * @return A list of all clients in the database.
     */
    public List<Client> findAll(){
        return clientDAO.findAll();
    }

    /**
     * Deletes a client from the database.
     * @param client The client to be deleted.
     */
    public void delete(Client client) {
        try {
            clientDAO.delete(client);
            AddController.showMessageDialog("The client was deleted successfully.", "Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserts a new client into the database using the provided fields.
     * First, it validates the data. Then, it creates a new Client object and sets its attributes based on the provided fields.
     * Finally, it attempts to insert the client into the database using the ClientDAO. If successful, it shows a success message.
     * @param fields A list of fields containing information about the client to be inserted.
     */
    public void insert(List<Object> fields) {
        validateData(fields);
        Client client = new Client();
        client.setId((int) fields.get(0));
        client.setName((String) fields.get(1));
        client.setAge(Integer.parseInt((String) fields.get(2)));
        client.setAddress((String) fields.get(3));
        client.setEmail((String) fields.get(4));
        try {
            clientDAO.insert(client);
            AddController.showMessageDialog("The client was inserted successfully!", "Success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits an existing client in the database.
     * @param fields The list of fields containing updated client information.
     * @param client The client object to be updated.
     */
    public void edit(List<Object> fields, Client client) {
        validateData(fields);
        client.setId((int) fields.get(0));
        client.setName((String) fields.get(1));
        client.setAge(Integer.parseInt((String) fields.get(2)));
        client.setAddress((String) fields.get(3));
        client.setEmail((String) fields.get(4));
        try {
            clientDAO.update(client);
            AddController.showMessageDialog("Successful update!", "Success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Validates client data before performing operations on the database.
     * @param fields The list of fields containing client information.
     */
    public void validateData(List<Object> fields){
        if (fields.size() != 5) {
            throw new IllegalArgumentException("Invalid number of fields for Client object");
        }
        Client client = new Client();
        client.setAge(Integer.parseInt((String) fields.get(2)));
        client.setEmail((String) fields.get(4));
        ClientValidator clientValidator = new ClientValidator();
        clientValidator.validate(client);
    }
}
