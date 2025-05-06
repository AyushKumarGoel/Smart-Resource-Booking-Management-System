package entity;

public class Booking {
    private String bookingId;
    private String userId;
    private String resourceId;
    private int startTime;
    private int endTime;
    private double cost;

    public Booking(String bookingId, String userId, String resourceId, int startTime, int endTime, double cost) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.resourceId = resourceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cost = cost;
    }

    public String getBookingId() { return bookingId; }
    public String getUserId() { return userId; }
    public String getResourceId() { return resourceId; }
    public int getStartTime() { return startTime; }
    public int getEndTime() { return endTime; }
    public double getCost() { return cost; }
}