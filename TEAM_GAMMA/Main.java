import controller.*;
import database.Database;
import entity.*;
import java.util.*;
import services.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserService userService = new UserService(Database.userRepository);
        UserController userController = new UserController(userService);
        ResourceController resourceController = new ResourceController(new ResourceService(Database.resourceRepository));
        BookingController bookingController = new BookingController(new BookingService(Database.bookingRepository), new CalculatorService());
        ReportController reportController = new ReportController(new ReportService(Database.bookingRepository));

        // Dummy users
        userController.registerUser(new Admin("AdminUser", "admin@mail.com", "admin123"));
        userController.registerUser(new ResourceManager("1", "ManagerUser", "manager@mail.com", "manager123"));
        userController.registerUser(new RegularUser("2", "RegularUser", "user@mail.com", "user123"));

        while (true) {
            System.out.println("\n=== Login ===");
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            User user = userController.login(email, password);
            if (user == null) {
                System.out.println("Invalid credentials. Try again.");
                continue;
            }

            while (true) {
                System.out.println("\nLogged in as: " + user.getClass().getSimpleName());
                if (user instanceof Admin) {
                    System.out.println("1. Register User");
                    System.out.println("2. Generate Report");
                    System.out.println("3. View All Users");
                    System.out.println("4. Switch Role");
                    System.out.println("5. Exit");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice == 1) {
                        System.out.print("Enter Role (Admin/Manager/User): ");
                        String role = scanner.nextLine().toLowerCase();
                    
                        System.out.print("Name: "); 
                        String name = scanner.nextLine();
                        System.out.print("Email: "); 
                        String mail = scanner.nextLine();
                        System.out.print("Password: "); 
                        String pass = scanner.nextLine();
                    
                        User newUser = switch (role) {
                            case "admin" -> new Admin(name, mail, pass);
                            case "manager" -> new ResourceManager("0", name, mail, pass); // Temporary ID for Manager
                            case "user" -> new RegularUser("0", name, mail, pass); // Temporary ID for User
                            default -> null;
                        };
                    
                        if (newUser != null) {
                            userController.registerUser(newUser);
                            System.out.println(role + " registered successfully!");
                        } else {
                            System.out.println("Invalid role.");
                        }
                    }
                    else if (choice == 2) {
                        reportController.generateReport();
                    }else if(choice ==3){
                        userController.viewAllUsers();
                    }
                     else if (choice == 4) {
                        break; // to outer loop for login
                    } else {
                        System.exit(0);
                    }

                } else if (user instanceof ResourceManager) {
                    System.out.println("1. Add Resource");
                    System.out.println("2. View Resources");
                    System.out.println("3. Switch Role");
                    System.out.println("4. Exit");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice == 1) {
                        System.out.print("ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Type: ");
                        String type = scanner.nextLine();
                        System.out.print("Cost/Hour: ");
                        double cost = scanner.nextDouble();
                        scanner.nextLine();

                        resourceController.addResource(new Resource(id, name, type, cost));
                        System.out.println("Resource added successfully.");
                    } else if (choice == 2) {
                        resourceController.viewResources();
                    } else if (choice == 3) {
                        break;
                    } else {
                        System.exit(0);
                    }

                } else if (user instanceof RegularUser) {
                    System.out.println("1. Book Resource");
                    System.out.println("2. View Bookings");
                    System.out.println("3. Switch Role");
                    System.out.println("4. Exit");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice == 1) {
                        System.out.print("Booking ID: ");
                        String bid = scanner.nextLine();
                        System.out.print("Resource ID: ");
                        String rid = scanner.nextLine();
                        System.out.print("Start Time (ms): ");
                        long startMs = scanner.nextLong();
                        System.out.print("End Time (ms): ");
                        long endMs = scanner.nextLong();
                        scanner.nextLine();

                        Resource res = Database.resourceRepository.getAllResources().stream()
                                .filter(r -> r.getId().equals(rid)).findFirst().orElse(null);

                        if (res != null) {
                            bookingController.bookResource(bid, String.valueOf(user.getId()), rid, new Date(startMs), new Date(endMs), res.getCostPerHour());
                            System.out.println("Resource booked successfully.");
                        } else {
                            System.out.println("Resource not found.");
                        }
                    } else if (choice == 2) {
                        bookingController.viewBookings();
                    } else if (choice == 3) {
                        break;
                    } else {
                        System.exit(0);
                    }
                }
            }
        }
    }
}
