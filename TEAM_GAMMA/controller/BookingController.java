package controller;

import entity.*;
import services.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class BookingController {
    private BookingService bookingService;
    private CalculatorService calculator;
    private int bookingCounter = 1;

    public BookingController(BookingService service, CalculatorService calc) {
        this.bookingService = service;
        this.calculator = calc;
    }

    public void bookResource(String userId, String resourceId, long start, long end, double costPerHour) {
        if (start >= end) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }

        for (Booking existing : bookingService.getBookings()) {
            if (existing.getResourceId().equals(resourceId)) {
                if (start < existing.getEndTime() && end > existing.getStartTime()) {
                    throw new IllegalArgumentException("This resource is already booked during the selected time.");
                }
            }
        }

        double hours = (end - start) / (1000.0 * 60 * 60); // milliseconds to hours
        double cost = calculator.calculateCost(hours, costPerHour);
        String bookingId = String.format("B%03d", bookingCounter++);

        // ✅ Corrected to pass long values (no casting to int)
        Booking b = new Booking(bookingId, userId, resourceId, start, end, cost);
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
                LocalDateTime startTime = Instant.ofEpochMilli(b.getStartTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime endTime = Instant.ofEpochMilli(b.getEndTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();

                System.out.println("Booking ID: " + b.getBookingId() + ", Resource ID: " + b.getResourceId() +
                        ", Start: " + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                        ", End: " + endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                        ", Cost: " + b.getCost());
            }
        }

        if (!found) {
            System.out.println("You have no bookings.");
        }
    }

    public boolean deleteBooking(String bookingId) {
        return bookingService.deleteBooking(bookingId);
    }

    public boolean updateBooking(String bookingId, long start, long end) {
        return bookingService.updateBooking(bookingId, start, end);
    }
}
