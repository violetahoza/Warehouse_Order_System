package Model;

public class Client {
    private int id;
    private String name, address, email, phone;

    public Client(){};

    public Client(int id, String name, String address, String email, String phone){
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }
}
