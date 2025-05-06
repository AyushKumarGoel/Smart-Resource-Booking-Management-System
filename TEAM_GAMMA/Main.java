import controller.*;
import database.Database;
import entity.*;
import java.util.*;
import java.util.regex.*; // <-- Added for email validation
import services.*;

public class Main {

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

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

                    int choice;
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid number.");
                        scanner.nextLine();
                        continue;
                    }

                    if (choice == 1) {
                        System.out.print("Enter Role (Admin/Manager/User): ");
                        String role = scanner.nextLine().toLowerCase();

                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Email: ");
                        String mail = scanner.nextLine();

                        if (!isValidEmail(mail)) {
                            System.out.println("Invalid email format. Please try again.");
                            continue;
                        }

                        System.out.print("Password: ");
                        String pass = scanner.nextLine();

                        User newUser = switch (role) {
                            case "admin" -> new Admin(name, mail, pass);
                            case "manager" -> new ResourceManager("0", name, mail, pass);
                            case "user" -> new RegularUser("0", name, mail, pass);
                            default -> null;
                        };

                        if (newUser != null) {
                            boolean isRegisteredUser = userController.registerUser(newUser);
                            if (isRegisteredUser) {
                                System.out.println("User registered Successfully");
                            } else {
                                System.out.println("User already exists");
                            }
                        } else {
                            System.out.println("Invalid role.");
                        }
                    } else if (choice == 2) {
                        reportController.generateReport();
                    } else if (choice == 3) {
                        userController.viewAllUsers();
                    } else if (choice == 4) {
                        break;
                    } else if (choice == 5) {
                        System.exit(0);
                    } else {
                        System.out.println("Invalid choice. Try again.");
                    }

                } else if (user instanceof ResourceManager) {
                    System.out.println("1. Add Resource");
                    System.out.println("2. View Resources");
                    System.out.println("3. Switch Role");
                    System.out.println("4. Exit");

                    int choice;
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid number.");
                        scanner.nextLine();
                        continue;
                    }

                    if (choice == 1) {
                        System.out.print("ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Type: ");
                        String type = scanner.nextLine();
                    
                        double cost;
                        try {
                            System.out.print("Cost/Hour: ");
                            cost = scanner.nextDouble();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid cost.");
                            scanner.nextLine();
                            continue;
                        }
                    
                        try {
                            resourceController.addResource(new Resource(id, name, type, cost, user.getId()));
                            System.out.println("Resource added successfully.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());  // This prints: Resource with this ID already exists.
                        }
                    }
                    
                    else if (choice == 2) {
                        resourceController.viewResourcesByUser(String.valueOf(user.getId()));
                    } else if (choice == 3) {
                        break;
                    } else if (choice == 4) {
                        System.exit(0);
                    } else {
                        System.out.println("Invalid choice. Try again.");
                    }

                } else if (user instanceof RegularUser) {
                    System.out.println("1. Book Resource");
                    System.out.println("2. View Bookings");
                    System.out.println("3. Switch Role");
                    System.out.println("4. View Resources");
                    System.out.println("5. Exit");

                    int choice;
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid number.");
                        scanner.nextLine();
                        continue;
                    }

                    if (choice == 1) {
                        System.out.print("Booking ID: ");
                        String bid = scanner.nextLine();
                        System.out.print("Resource ID: ");
                        String rid = scanner.nextLine();

                        long startMs, endMs;
                        try {
                            System.out.print("Start Time (hrs): ");
                            startMs = scanner.nextLong();
                            System.out.print("End Time (hrs): ");
                            endMs = scanner.nextLong();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter time in hours.");
                            scanner.nextLine();
                            continue;
                        }

                        Resource res = Database.resourceRepository.getAllResources().stream()
                                .filter(r -> r.getId().equals(rid)).findFirst().orElse(null);

                        if (res != null) {
                            try {
                                bookingController.bookResource(bid, String.valueOf(user.getId()), rid, (int) startMs, (int) endMs, res.getCostPerHour());
                                System.out.println("Resource booked successfully.");
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());  // Will print "Booking with this ID already exists."
                            }
                            
                        } else {
                            System.out.println("Resource not found.");
                        }
                    } else if (choice == 2) {
                        bookingController.viewBookings();
                    } else if (choice == 3) {
                        break;
                    } else if (choice == 4) {
                        resourceController.viewResources();
                    } else if (choice == 5) {
                        System.exit(0);
                    } else {
                        System.out.println("Invalid choice. Try again.");
                    }
                }
            }
        }
    }
}
