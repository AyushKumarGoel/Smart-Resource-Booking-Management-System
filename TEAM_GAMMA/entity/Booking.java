package entity;

public class Booking {
    private String bookingId;
    private String userId;
    private String resourceId;
    private long startTime;
    private long endTime;
    private double cost;

    public Booking(String bookingId, String userId, String resourceId, long startTime, long endTime, double cost) {
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
    public long getStartTime() { return startTime; }
    public long getEndTime() { return endTime; }
    public double getCost() { return cost; }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    
    public void setCost(double cost) {
        this.cost = cost;
    }
    
}