package services;

import entity.*;
import repository.*;
import java.util.*;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository repo) {
        this.userRepository = repo;
    }

    public void registerUser(User user) {
        if (isEmailTaken(user.getEmail())) {
            System.out.println("Email already in use.");
            return;
        }
        userRepository.addUser(user);
    }

    private boolean isEmailTaken(String email) {
        return userRepository.getAllUsers().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public List<User> getUsers() {
        return userRepository.getAllUsers();
    }

    public User login(String email, String password) {
        return userRepository.authenticate(email, password);
    }

    public User getUserById(String id) {
        return userRepository.getUserById(id);
    }
}