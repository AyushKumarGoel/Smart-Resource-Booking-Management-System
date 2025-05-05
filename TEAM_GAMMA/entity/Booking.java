package entity;

import java.util.Date;

public class Booking {
    private String bookingId;
    private String userId;
    private String resourceId;
    private Date startTime;
    private Date endTime;
    private double cost;

    public Booking(String bookingId, String userId, String resourceId, Date startTime, Date endTime, double cost) {
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
    public Date getStartTime() { return startTime; }
    public Date getEndTime() { return endTime; }
    public double getCost() { return cost; }
}