package srmbs.models;

import java.time.LocalDateTime;

public class Booking {
    private User user;
    private Resource resource;
    private LocalDateTime start;
    private LocalDateTime end;
    private double cost;

    public Booking(User user, Resource resource, LocalDateTime start, LocalDateTime end, double cost) {
        this.user = user;
        this.resource = resource;
        this.start = start;
        this.end = end;
        this.cost = cost;
    }

    public void displayBooking() {
        System.out.println("User: " + user.getUsername() + ", Resource: " + resource.getName()
                + ", From: " + start + ", To: " + end + ", Cost: ₹" + cost);
    }
}