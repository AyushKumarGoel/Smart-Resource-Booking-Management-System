package services;

import entity.*;
import repository.*;
import java.util.*;

public class BookingService {
    private BookingRepository bookingRepo;

    public BookingService(BookingRepository repo) { this.bookingRepo = repo; }
    public void addBooking(Booking booking) { bookingRepo.addBooking(booking); }
    public List<Booking> getBookings() { return bookingRepo.getAllBookings(); }
}
