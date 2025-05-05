package controller;

import entity.*;
import services.*;

public class UserController {
    private UserService userService;

    public UserController(UserService service) { this.userService = service; }

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
}