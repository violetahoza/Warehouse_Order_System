package model;

/** Represents a client entity.*/
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
    public String getEmail() {
        return email;
    }
    public int getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return id + " | " + name + " | " + age + " | " + address + " | " + email;
    }
}
