package database;

import repository.*;

public class Database {
    public static UserRepository userRepository = new UserRepository();
    public static ResourceRepository resourceRepository = new ResourceRepository();
    public static BookingRepository bookingRepository = new BookingRepository();
}