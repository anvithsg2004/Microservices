package com.example.UserService.UserService.service;

import com.example.UserService.UserService.entity.User;
import com.example.UserService.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create User
    public User createUser(User user) {
        user.setUid(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    // Update User
    public User updateUser(String uid, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(uid);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAbout(updatedUser.getAbout());
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id: " + uid);
        }
    }

    // Delete User
    public void deleteUser(String uid) {
        userRepository.deleteById(uid);
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get User by ID
    public User getUserById(String uid) {
        return userRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + uid));
    }
}
