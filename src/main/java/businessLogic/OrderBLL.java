package businessLogic;

import dataAccess.BillDAO;
import dataAccess.OrderDAO;
import model.*;
import presentation.AddController;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
/** Business Logic class for handling operations related to orders. It provides functionalities for inserting new orders into the database.*/
public class OrderBLL {
    OrderDAO orderDAO = new OrderDAO(Order.class); // the data access object for interacting with the order database table
    public OrderBLL(){}

    /**
     * Inserts a new order into the database if the quantity is valid. If the order is successfully inserted, it displays a success message.
     * Additionally, it creates a bill for the order and logs it to a file.
     * @param order The order to be inserted.
     * @return true if the order was successfully inserted, false otherwise.
     * @throws SQLException If an SQL error occurs while interacting with the database.
     */
    public boolean insert(Order order) throws SQLException {
        ProductBLL productBLL = new ProductBLL();
        Product product = productBLL.productDAO.findById(order.getProductID());
        // Check if the order quantity is valid and does not exceed the available stock
        if(order.getQuantity() != 0 && order.getQuantity() <= product.getQuantity()) {
            orderDAO.insert(order); // insert the order into the database
            AddController.showMessageDialog("The order was placed successfully!", "Success");

            ClientBLL clientBLL = new ClientBLL();
            Client client = clientBLL.clientDAO.findById(order.getClientID()); // retrieve client information
            // generate a bill for the order
            Bill bill = new Bill(order.getOrderID(), client, product, (double) product.getPrice() * order.getQuantity(), LocalDateTime.now());
            try {
                Log.appendBillToFile(bill); // log the bill to a file
                BillDAO billDAO = new BillDAO();
                billDAO.insertBill(bill);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            AddController.showErrorDialog("Under-stock for " + product.getName() + "\nMax quantity = " + product.getQuantity(), "Under-stock");
            throw new RuntimeException("Under-stock for " + product.getName() + "\nMax quantity = " + product.getQuantity());
        }
    }
}
