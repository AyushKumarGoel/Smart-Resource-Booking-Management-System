package srmbs.models;

public class Resource {
    int id;
    String name;
    String type;
    double costPerHour;
    boolean available;

    public Resource(int id, String name, String type, double costPerHour) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.costPerHour = costPerHour;
        this.available = true;
    }

    public void display() {
        System.out.println("ID: " + id + ", Name: " + name + ", Type: " + type + ", Cost/Hr: Rs. " + costPerHour + ", Available: " + available);
    }
}
