package services;

import entity.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

            LocalDateTime startTime = Instant.ofEpochMilli(b.getStartTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime endTime = Instant.ofEpochMilli(b.getEndTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();

            User user = userRepo.getUserById(b.getUserId());
            System.out.println("Booking ID: " + b.getBookingId() +
                ", Resource ID: " + b.getResourceId() +
                ", Cost: " + b.getCost() +
                ", User ID: " + b.getUserId() +
                ", Name: " + user.getName() +
                ", Start: " + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                ", End: " + endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
    }
}

