package controller;

import entity.*;
import repository.*;
import services.*;

public class BookingController {
    private BookingService bookingService;
    private CalculatorService calculator;
    private UserRepository userRepo; // Assuming you have a UserRepository to fetch user details
    private int bookingCounter = 1; // Auto-increment counter for booking IDs

    public BookingController(BookingService service, CalculatorService calc, UserRepository userRepo) {
        this.bookingService = service;
        this.calculator = calc;
        this.userRepo = userRepo;
    }

    public void bookResource(String userId, String resourceId, int start, int end, double costPerHour) {
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
        String bookingId = String.format("B%03d", bookingCounter++); // Auto-generated ID like B001, B002, etc.

        User user = userRepo.getUserById(userId);

        Booking b = new Booking(bookingId, userId, resourceId, start, end, cost, user);

        bookingService.addBooking(b);
        System.out.println("Booking Confirmed with ID: " + bookingId + " and cost: " + cost);
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
    public boolean deleteBooking(String bookingId) {
        return bookingService.deleteBooking(bookingId);
    }
    
    public boolean updateBooking(String bookingId, int start, int end) {
        return bookingService.updateBooking(bookingId, start, end);
    }
    
}
