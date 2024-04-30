package Model;

public class Order {
    private int clientID, productID, quantity;

    public Order(int clientID, int productID, int quantity){
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
    }
}
