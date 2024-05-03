package BusinessLogic;

import DataAccess.AbstractDAO;
import DataAccess.OrderDAO;
import Model.*;
import Presentation.AddController;
import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
/**
 * Business Logic class for handling operations related to orders.
 * It provides functionalities for inserting new orders into the database.
 */
public class OrderBLL {
    OrderDAO orderDAO = new OrderDAO(Order.class);
    public OrderBLL(){}

    /**
     * Inserts a new order into the database if the quantity is valid. If the order is successfully inserted, it displays a success message.
     * Additionally, it creates a bill for the order and logs it to a file.
     * @param order The order to be inserted.
     * @param product The product associated with the order.
     * @return true if the order was successfully inserted, false otherwise.
     * @throws SQLException If an SQL error occurs while interacting with the database.
     */
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
