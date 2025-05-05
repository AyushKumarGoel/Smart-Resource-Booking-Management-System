package srmbs.models;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, "Admin");
    }

    @Override
    public void showMenu() {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. View Reports");
        System.out.println("2. Manage Users");
    }
}
