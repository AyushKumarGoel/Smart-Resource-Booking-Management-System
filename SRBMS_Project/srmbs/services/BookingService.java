package srmbs.services;

import srmbs.models.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private List<Booking> bookings = new ArrayList<>();

    public boolean isAvailable(Resource resource, LocalDateTime start, LocalDateTime end) {
        DateTimeRange newRange = new DateTimeRange(start, end);
        for (Booking b : bookings) {
            if (b.resource.getId() == resource.getId()) {
                DateTimeRange existingRange = new DateTimeRange(b.start, b.end);
                if (newRange.overlaps(existingRange)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Booking bookResource(User user, Resource resource, LocalDateTime start, LocalDateTime end) {
        if (!isAvailable(resource, start, end)) {
            System.out.println("Error: The resource is already booked in the selected time slot.");
            return null;
        }
        long duration = Duration.between(start, end).toHours();
        double cost = duration * resource.getCostPerHour();
        Booking booking = new Booking(user, resource, start, end, cost);
        bookings.add(booking);
        System.out.println("Booking confirmed!");
        booking.displayBooking();
        return booking;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }
}