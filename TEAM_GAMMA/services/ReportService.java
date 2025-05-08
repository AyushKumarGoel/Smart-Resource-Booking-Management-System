package services;

import entity.*;
import repository.*;

public class ReportService {
    private BookingRepository bookingRepo;
    private UserRepository userRepo;

    public ReportService(BookingRepository bookingRepo, UserRepository userRepo) {
        this.bookingRepo = bookingRepo;
        this.userRepo = userRepo;
    }

    public void generateReport() {
        for (Booking b : bookingRepo.getAllBookings()) {
            User user = userRepo.getUserById(b.getUserId());
            System.out.println("Booking ID: " + b.getBookingId() +
                ", Resource ID: " + b.getResourceId() +
                ", Cost: " + b.getCost() +
                ", User ID: " + b.getUserId() +
                ", Name: " + user.getName() +
                ", Start: " + b.getStartTime() +
                ", End: " + b.getEndTime());
        }
    }
}

