package Model;

public class Client {
    private int id, age;
    private String name, address, email;

    public Client(){};

    public Client(int id, int age, String name, String address, String email){
        this.id = id;
        this.age = age;
        this.name = name;
        this.address = address;
        this.email = email;
    }
}
