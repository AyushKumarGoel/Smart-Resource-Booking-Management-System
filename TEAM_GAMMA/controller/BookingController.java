package controller;

import entity.*;
import services.*; 

public class BookingController {
    private BookingService bookingService;
    private CalculatorService calculator;

    public BookingController(BookingService service, CalculatorService calc) {
        this.bookingService = service;
        this.calculator = calc;
    }

    public void bookResource(String bookingId, String userId, String resourceId, int start, int end, double costPerHour) {
        // Validate start < end
        if (start >= end) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
    
        // Check for existing bookings on the same resource
        for (Booking existing : bookingService.getBookings()) {
            if (existing.getResourceId().equals(resourceId)) {
                // If existing booking overlaps
                if (start < existing.getEndTime() && end > existing.getStartTime()) {
                    throw new IllegalArgumentException("This resource is already booked during the selected time.");
                }
            }
        }
    
        double hours = end - start;
        double cost = calculator.calculateCost(hours, costPerHour);
        Booking b = new Booking(bookingId, userId, resourceId, start, end, cost);
        bookingService.addBooking(b);
        System.out.println("Booking Confirmed with cost: " + cost);
    }
    
    

    public void viewBookings() {
        for (Booking b : bookingService.getBookings()) {
            System.out.println("Booking ID: " + b.getBookingId() + ", User ID: " + b.getUserId());
        }
    }

    public void viewBookingsByUser(String userId) {
        boolean found = false;
        for (Booking b : bookingService.getBookings()) {
            if (b.getUserId().equals(userId)) {
                found = true;
                System.out.println("Booking ID: " + b.getBookingId() + ", Resource ID: " + b.getResourceId() +
                        ", Start: " + b.getStartTime() + ", End: " + b.getEndTime() + ", Cost: " + b.getCost());
            }
        }
        if (!found) {
            System.out.println("You have no bookings.");
        }
    }
    
}