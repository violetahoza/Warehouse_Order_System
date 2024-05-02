package BusinessLogic;

import DataAccess.AbstractDAO;
import DataAccess.OrderDAO;
import Model.*;
import Presentation.AddController;
import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrderBLL {
    OrderDAO orderDAO = new OrderDAO(Order.class);
    public OrderBLL(){}

    public OrderBLL(int client, int product, int quantity) throws NumberFormatException{
        if(quantity <= 0)
        {
            AddController.showErrorDialog("The quantity must be greater than 0!", "Input error");
            throw new IllegalArgumentException("The quantity must be greater than 0!");
        }
    }
    public boolean insert(Order order, Product product) throws SQLException {
        if(order.getQuantity() != 0 && order.getQuantity() <= product.getQuantity()) {
            orderDAO.insert(order);
            AddController.showMessageDialog("The order was placed successfully!", "Success");

            ClientBLL clientBLL = new ClientBLL();
            Client client = clientBLL.clientDAO.findById(order.getClientID());
            Bill bill = new Bill(order.getOrderID(), client, product, (double) product.getPrice() * order.getQuantity(), LocalDateTime.now());
            try {
                Log.appendBillToFile(bill);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            AddController.showErrorDialog("Under-stock for " + product.getName() + "\nMax quantity = " + product.getQuantity(), "Under-stock");
            return false;
        }
    }
}
