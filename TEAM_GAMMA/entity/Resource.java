package entity;

public class Resource {
    private static int counter = 1;
    private String id;
    private String name;
    private String type;
    private double costPerHour;
    private String managerId;

    public Resource(String name, String type, double costPerHour, String managerId) {
        // Validate name
        if (name.matches("^\\d+$") || name.matches("^\\d.*")) {
            throw new IllegalArgumentException("Invalid resource name: must contain alphabet and cannot start with a digit.");
        }

        // Validate cost
        if (costPerHour < 0) {
            throw new IllegalArgumentException("Invalid cost: must be non-negative.");
        }

        this.id = "R" + (counter++);
        this.name = name;
        this.type = type;
        this.costPerHour = costPerHour;
        this.managerId = managerId;
    }

    // Getters
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public double getCostPerHour() {
        return costPerHour;
    }
    public String getManagerId() {
        return managerId;
    }


    // Add these setter methods below the getters
    public void setName(String name) {
        if (name.matches("^\\d+$") || name.matches("^\\d.*")) {
            throw new IllegalArgumentException("Invalid resource name: must contain alphabet and cannot start with a digit.");
        }
        this.name = name;
    }

    public void setCostPerHour(double costPerHour) {
        if (costPerHour < 0) {
            throw new IllegalArgumentException("Invalid cost: must be non-negative.");
        }
        this.costPerHour = costPerHour;
    }

}
