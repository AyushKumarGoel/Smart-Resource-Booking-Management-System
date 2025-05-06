package entity;

public class Resource {
    private String id;
    private String name;
    private String type;
    private double costPerHour;
    private String addedByUserId; 

    public Resource(String id, String name, String type, double costPerHour, String addedByUserId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.costPerHour = costPerHour;
        this.addedByUserId = addedByUserId;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public double getCostPerHour() { return costPerHour; }
    public String getAddedByUserId() { return addedByUserId; }
}
