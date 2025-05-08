package repository;

import entity.Booking;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
    private final List<Booking> bookings = new ArrayList<>();

    public void addBooking(Booking newBooking) {
        for (Booking existing : bookings) {
            // Booking ID must be unique
            if (existing.getBookingId().equals(newBooking.getBookingId())) {
                throw new IllegalArgumentException("Booking ID already exists.");
            }

            // Same resource -> check time conflict
            if (existing.getResourceId().equals(newBooking.getResourceId())) {
                long newStart = newBooking.getStartTime();
                long newEnd = newBooking.getEndTime();
                long existingStart = existing.getStartTime();
                long existingEnd = existing.getEndTime();

                boolean isOverlapping = newStart < existingEnd && newEnd > existingStart;
                if (isOverlapping) {
                    throw new IllegalArgumentException("Resource is already booked during this time.");
                }
            }
        }

        bookings.add(newBooking);
    }
    public boolean deleteBooking(String bookingId) {
        return bookings.removeIf(b -> b.getBookingId().equals(bookingId));
    }
    
    public boolean updateBooking(String bookingId, long newStartMs, long newEndMs) {
        for (Booking b : bookings) {
            if (b.getBookingId().equals(bookingId)) {
                b.setStartTime(newStartMs);
                b.setEndTime(newEndMs);
                double hours = (newEndMs - newStartMs) / (1000.0 * 60 * 60); // convert ms to hours
                double costPerHour = b.getCost() / ((b.getEndTime() - b.getStartTime()) / (1000.0 * 60 * 60));
                b.setCost(hours * costPerHour);
                return true;
            }
        }
        return false;
    }
    
    

    public List<Booking> getAllBookings() {
        return bookings;
    }
}
