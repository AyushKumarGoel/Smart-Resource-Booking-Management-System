package srmbs.models;

public class RegularUser extends User {
    public RegularUser(String username, String password) {
        super(username, password, "RegularUser");
    }

    @Override
    public void showMenu() {
        System.out.println("\n--- Regular User Menu ---");
        System.out.println("1. Browse Resources");
        System.out.println("2. Book Resource");
        System.out.println("3. Cancel Booking");
    }
}
