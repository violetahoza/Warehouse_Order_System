package Model;

public class Product {
    private int id, quantity;
    private double price;
    private String name;

    public Product(){};

    public Product(int id, String name, double price, int quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public int getId() {
        return id;
    }
    public double getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    public String toString(){
        return id + " | " + name + " | " + price + " | " + quantity;
    }
}
