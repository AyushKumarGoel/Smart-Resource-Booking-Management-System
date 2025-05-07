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

    public static boolean isValidName(String name) {
        String nameRegex = "^[A-Za-z][A-Za-z0-9]*$"; // Name should not start with a digit and must contain characters
        Pattern pattern = Pattern.compile(nameRegex);
        return pattern.matcher(name).matches();
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
                    System.out.println("4. Delete User");
                    System.out.println("5. Modify User");
                    System.out.println("6. Switch Role");
                    System.out.println("7. Exit");

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

                        if (!isValidName(name)) {
                            System.out.println("Invalid name. It must start with a letter and contain only letters and digits.");
                            continue;
                        }

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
                    } else if (choice == 6) {
                        break;
                    } 
                    else if (choice == 4) {
                        System.out.print("Enter email to delete user: ");
                        String emailToDelete = scanner.nextLine();
                        if (userController.deleteUser(emailToDelete)) {
                            System.out.println("User deleted successfully.");
                        } else {
                            System.out.println("User not found.");
                        }
                    }
                    else if (choice == 5) {
                        System.out.print("Enter email of the user to modify: ");
                        String e = scanner.nextLine();
                        System.out.print("New Name: ");
                        String newName = scanner.nextLine();
                        System.out.print("New Password: ");
                        String newPassword = scanner.nextLine();
                        if (userController.updateUser(e, newName, newPassword)) {
                            System.out.println("User updated successfully.");
                        } else {
                            System.out.println("User not found.");
                        }
                    }
                    else if (choice == 7) {
                        System.out.println("TATA BYE BYE");
                        System.out.println("Exiting the application...");
                        System.exit(0);
                    } else {
                        System.out.println("Invalid choice. Try again.");
                    }

                } else if (user instanceof ResourceManager) {
                    System.out.println("1. Add Resource");
                    System.out.println("2. View Resources");
                    System.out.println("3. Delete Resource");
                    System.out.println("4. Modify Resource");
                    System.out.println("5. Switch Role");
                    System.out.println("6. Exit");

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
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Type: ");
                        System.out.println("");
                        System.out.println("1. Room");
                        System.out.println("2. Book");
                        System.out.println("3. Equipments");
                        int q = scanner.nextInt();
                        String type;
                        switch (q) {
                            case 1:
                                type = "Room";
                                break;
                            case 2:
                                type = "Book";
                                break;
                            case 3:
                                type = "Equipments";
                                break;
                            default:
                                System.err.println("Invalid type. Please try again.");
                                continue;
                        }

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
                            resourceController.addResource(new Resource(name, type, cost, user.getId()));
                            System.out.println("Resource added successfully.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());  // This prints: Resource with this ID already exists.
                        }
                    }

                    else if (choice == 2) {
                        resourceController.viewResourcesByUser(String.valueOf(user.getId()));
                    } 
                    else if (choice == 3) {
                        System.out.print("Enter Resource ID to delete: ");
                        String resId = scanner.nextLine();
                        if (resourceController.deleteResource(resId)) {
                            System.out.println("Resource deleted successfully.");
                        } else {
                            System.out.println("Resource not found.");
                        }
                    }
                    else if (choice == 4) {
                        System.out.print("Enter Resource ID to modify: ");
                        String resId = scanner.nextLine();
                        System.out.print("New Name: ");
                        String newName = scanner.nextLine();
                        System.out.print("New Cost per Hour: ");
                        double newCost = scanner.nextDouble(); scanner.nextLine();
                        if (resourceController.updateResource(resId, newName, newCost)) {
                            System.out.println("Resource updated successfully.");
                        } else {
                            System.out.println("Resource not found.");
                        }
                    }else if (choice == 5) {
                        break;
                    } else if (choice == 6) {
                        System.out.println("TATA BYE BYE");
                        System.out.println("Exiting the application...");
                        System.exit(0);
                    } else {
                        System.out.println("Invalid choice. Try again.");
                    }

                } else if (user instanceof RegularUser) {
                    System.out.println("1. Book Resource");
                    System.out.println("2. View Bookings");
                    System.out.println("3. View Resources");
                    System.out.println("4. Delete Booking");
                    System.out.println("5. Modify Booking");
                    System.out.println("6. Switch Role");
                    System.out.println("7. Exit");

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
                                bookingController.bookResource(String.valueOf(user.getId()), rid, (int) startMs, (int) endMs, res.getCostPerHour());
                                System.out.println("Resource booked successfully.");
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());  // Will print "Booking with this ID already exists."
                            }
                            
                        } else {
                            System.out.println("Resource not found.");
                        }
                    } else if (choice == 2) {
                        bookingController.viewBookingsByUser(String.valueOf(user.getId()));
                    } else if (choice == 6) {
                        break;
                    } else if (choice == 3) {
                        resourceController.viewResources();
                    }
                    else if (choice == 4) {
                        System.out.print("Enter Booking ID to delete: ");
                        String bid = scanner.nextLine();
                        if (bookingController.deleteBooking(bid)) {
                            System.out.println("Booking deleted successfully.");
                        } else {
                            System.out.println("Booking not found.");
                        }
                    }
                    else if (choice == 5) {
                        System.out.print("Enter Booking ID to update: ");
                        String bid = scanner.nextLine();
                        System.out.print("New Start Time (hrs): ");
                        int newStart = scanner.nextInt();
                        System.out.print("New End Time (hrs): ");
                        int newEnd = scanner.nextInt();
                        scanner.nextLine();
                        if (bookingController.updateBooking(bid, newStart, newEnd)) {
                            System.out.println("Booking updated successfully.");
                        } else {
                            System.out.println("Booking not found.");
                        }
                    } 
                    else if (choice == 7) {
                        System.out.println("TATA BYE BYE");
                        System.out.println("Exiting the application...");
                        System.exit(0);
                    } else {
                        System.out.println("Invalid choice. Try again.");
                    }
                }
            }
        }
    }
}
