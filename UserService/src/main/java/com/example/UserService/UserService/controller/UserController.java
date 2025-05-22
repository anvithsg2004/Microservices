package com.example.UserService.UserService.controller;

import com.example.UserService.UserService.entity.User;
import com.example.UserService.UserService.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Get user by ID
    @GetMapping("/{uid}")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "getUserByIdFallback")
    @RateLimiter(name = "userServiceRateLimiter", fallbackMethod = "getUserByIdRateLimitFallback")
    public ResponseEntity<User> getUserById(@PathVariable String uid) {
        User user = userService.getUserById(uid);
        return ResponseEntity.ok(user);
    }

    // Fallback method for Circuit Breaker
    public ResponseEntity<User> getUserByIdFallback(String uid, Throwable throwable) {
        // Log the error (optional)
        System.err.println("Fallback triggered for user ID " + uid + " due to: " + throwable.getMessage());

        // Return a default response or error message
        User fallbackUser = new User();
        fallbackUser.setUid(uid);
        fallbackUser.setName("Unknown User");
        fallbackUser.setEmail("N/A");
        fallbackUser.setAbout("The system is down. Try Later.");
        return ResponseEntity.status(503).body(fallbackUser); // 503 Service Unavailable
    }

    // Fallback method for Rate Limiter
    public ResponseEntity<User> getUserByIdRateLimitFallback(String uid, Throwable throwable) {
        System.err.println("Rate limit exceeded for user ID " + uid + ": " + throwable.getMessage());
        User rateLimitedUser = new User();
        rateLimitedUser.setUid(uid);
        rateLimitedUser.setName("Rate Limit Exceeded");
        rateLimitedUser.setEmail("N/A");
        rateLimitedUser.setAbout("Too many requests. Please try again later.");
        return ResponseEntity.status(429).body(rateLimitedUser); // 429 Too Many Requests
    }

    // Update user
    @PutMapping("/{uid}")
    public ResponseEntity<User> updateUser(@PathVariable String uid, @RequestBody User user) {
        User updatedUser = userService.updateUser(uid, user);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete user
    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> deleteUser(@PathVariable String uid) {
        userService.deleteUser(uid);
        return ResponseEntity.noContent().build();
    }
}
