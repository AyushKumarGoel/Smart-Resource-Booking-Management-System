package srmbs.services;

import java.util.Scanner;
import srmbs.models.Admin;

public class UserService {
    public void showLoginMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String user = scanner.nextLine();
        System.out.println("Enter password:");
        String pass = scanner.nextLine();

        if (user.equals("admin") && pass.equals("admin123")) {
            Admin admin = new Admin(user, pass);
            admin.showMenu();
        } else {
            System.out.println("Invalid login!");
        }
    }
}