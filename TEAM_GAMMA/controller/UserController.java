package controller;

import entity.*;
import java.util.List;
import services.*;

public class UserController {
    private UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

    public boolean registerUser(User user) {
        return userService.registerUser(user);

    }

    public User login(String email, String password) {
        User user = userService.login(email, password);
        if (user != null) {
            System.out.println("Login Successful: " + user.getName());
        } else {
            System.out.println("Invalid Credentials");
        }
        return user;
    }
    public void viewAllUsers() {
        List<User> users = userService.getUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("=== List of Users ===");
            for (User user : users) {
                System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail()
                        + ", Role: " + user.getClass().getSimpleName());
            }
        }
    }
    public boolean deleteUser(String email) {
        return userService.deleteUser(email);
    }
    
    public boolean updateUser(String email, String newName, String newPassword) {
        return userService.updateUser(email, newName, newPassword);
    }
    
}
