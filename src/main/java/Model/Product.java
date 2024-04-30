package Model;

public class Product {
    private int id, price, quantity;
    private String name;

    public Product(){};

    public Product(int id, String name, int price, int quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
