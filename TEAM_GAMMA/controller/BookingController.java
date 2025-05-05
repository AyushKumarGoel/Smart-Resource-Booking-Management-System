package controller;

import entity.*;
import java.util.Date;
import services.*;  // <-- Add this import

public class BookingController {
    private BookingService bookingService;
    private CalculatorService calculator;

    public BookingController(BookingService service, CalculatorService calc) {
        this.bookingService = service;
        this.calculator = calc;
    }

    public void bookResource(String bookingId, String userId, String resourceId, Date start, Date end, double costPerHour) {
        double hours = (end.getTime() - start.getTime()) / (1000.0 * 60 * 60);
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
}