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

public class ClientBLL {
    ClientDAO clientDAO = new ClientDAO(Client.class);
    public ClientBLL(){}
    public List<Client> findAll(){
        return clientDAO.findAll();
    }

    public void delete(Client client) {
        try {
            clientDAO.delete(client);
            AddController.showMessageDialog("The client was deleted successfully.", "Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
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
