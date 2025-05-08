package services;

import entity.*;
import java.util.*;
import repository.*;

public class BookingService {
    private BookingRepository bookingRepo;

    public BookingService(BookingRepository repo) { this.bookingRepo = repo; }
    public void addBooking(Booking booking) { bookingRepo.addBooking(booking); }
    public List<Booking> getBookings() { return bookingRepo.getAllBookings(); }
    public boolean deleteBooking(String bookingId) {
        return bookingRepo.deleteBooking(bookingId);
    }
    
    public boolean updateBooking(String bookingId, long start, long end) {
        return bookingRepo.updateBooking(bookingId, start, end);
    }
    
}
