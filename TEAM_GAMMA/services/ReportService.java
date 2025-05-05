package services;

import repository.BookingRepository;
import entity.Booking;

public class ReportService {
    private BookingRepository bookingRepo;

    public ReportService(BookingRepository repo) {
        this.bookingRepo = repo;
    }

    public void generateReport() {
        for (Booking b : bookingRepo.getAllBookings()) {
            System.out.println("Booking ID: " + b.getBookingId() + ", Resource ID: " + b.getResourceId() + ", Cost: " + b.getCost());
        }
    }
}
