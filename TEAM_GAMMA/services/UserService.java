package services;

import entity.*;
import java.util.*;
import repository.*;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository repo) {
        this.userRepository = repo;
    }

    public boolean registerUser(User user) {
        if (isEmailTaken(user.getEmail())) {
            return false;
        }
        else{userRepository.addUser(user);    return true;}
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
    public boolean deleteUser(String email) {
        return userRepository.deleteUserByEmail(email);
    }
    
    public boolean updateUser(String email, String newName, String newPassword) {
        return userRepository.updateUserDetails(email, newName, newPassword);
    }
    
}
