package controller;

import entity.*;
import services.*;

import java.util.List;

public class UserController {
    private UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

    public void registerUser(User user) {
        userService.registerUser(user);
        System.out.println("User Registered: " + user.getName());
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
}