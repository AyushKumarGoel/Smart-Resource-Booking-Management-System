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
                int newStart = newBooking.getStartTime();
                int newEnd = newBooking.getEndTime();
                int existingStart = existing.getStartTime();
                int existingEnd = existing.getEndTime();

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

    public boolean updateBooking(String bookingId, int newStart, int newEnd) {
        for (Booking b : bookings) {
            if (b.getUserId().equals(bookingId)) {
                b.setStartTime(newStart);
                b.setEndTime(newEnd);
                return true;
            }
        }
        return false;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }
}
