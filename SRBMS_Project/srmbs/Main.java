package srmbs;
import srmbs.*;
import java.util.*;

public class Main {
    static List<User> users = new ArrayList<>();
    static List<Resource> resources = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Seed some data
        users.add(new Admin("admin", "admin123"));
        users.add(new ResourceManager("manager", "manager123"));
        users.add(new RegularUser("user", "user123"));
        resources.add(new Resource(1, "Conference Room A", "Room", 500));
        resources.add(new Resource(2, "Projector", "Equipment", 100));

        System.out.println("=================================");
        System.out.println(" Welcome to SRBMS System ");
        System.out.println("=================================");

        System.out.print("Enter username: ");
        String uname = sc.nextLine();
        System.out.print("Enter password: ");
        String pwd = sc.nextLine();

        User loggedIn = authenticate(uname, pwd);

        if (loggedIn != null) {
            loggedIn.showMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    static User authenticate(String uname, String pwd) {
        for (User u : users) {
            if (u.username.equals(uname) && u.password.equals(pwd)) {
                return u;
            }
        }
        return null;
    }
}
