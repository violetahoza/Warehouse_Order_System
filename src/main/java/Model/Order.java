package Model;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents an order entity.
 */
public class Order {
    private int orderID, clientID, productID, quantity;

    public Order(int clientID, int productID, int quantity){
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
        this.orderID = ThreadLocalRandom.current().nextInt(1, 50000); // Generate a random order ID within a certain range
    }
    public int getQuantity() {
        return quantity;
    }
    public int getOrderID() {
        return orderID;
    }
    public int getClientID() {
        return clientID;
    }
}
