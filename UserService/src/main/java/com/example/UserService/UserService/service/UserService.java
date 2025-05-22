package com.example.UserService.UserService.service;

import com.example.UserService.UserService.entity.Hotel;
import com.example.UserService.UserService.entity.Rating;
import com.example.UserService.UserService.entity.User;
import com.example.UserService.UserService.feignClient.HotelService;
import com.example.UserService.UserService.feignClient.RatingService;
import com.example.UserService.UserService.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

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
        List<User> users = userRepository.findAll();

        // For each user, fetch their ratings and hotel details using the same resilient methods
        for (User user : users) {
            List<Rating> ratings = getUserRatings(user.getUid());
            for (Rating rating : ratings) {
                Hotel hotel = getHotelForRating(rating.getHotelId());
                rating.setHotel(hotel);
            }
            user.setRatings(ratings);
        }

        return users;
    }

    // Get User by ID
    public User getUserById(String uid) {
        // Fetch user from MongoDB
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + uid));

        // Fetch ratings with Resilience4j
        List<Rating> ratings = getUserRatings(uid);
        // Fetch hotel details for each rating
        for (Rating rating : ratings) {
            Hotel hotel = getHotelForRating(rating.getHotelId());
            rating.setHotel(hotel);
        }
        user.setRatings(ratings);

        return user;
    }

    @CircuitBreaker(name = "ratingServiceCircuitBreaker", fallbackMethod = "getUserRatingsFallback")
    @Retry(name = "ratingServiceRetry")
    private List<Rating> getUserRatings(String uid) {
        ResponseEntity<List<Rating>> ratingsResponse = ratingService.getRatingsByUserId(uid);
        return ratingsResponse.getBody() != null ? ratingsResponse.getBody() : Collections.emptyList();
    }

    private List<Rating> getUserRatingsFallback(String uid, Throwable throwable) {
        System.err.println("Fallback triggered for ratings of user " + uid + ": " + throwable.getMessage());
        return Collections.emptyList();
    }

    @CircuitBreaker(name = "hotelServiceCircuitBreaker", fallbackMethod = "getHotelForRatingFallback")
    @Retry(name = "hotelServiceRetry")
    private Hotel getHotelForRating(String hotelId) {
        ResponseEntity<Hotel> hotelResponse = hotelService.getHotelById(hotelId);
        return hotelResponse.getBody();
    }

    private Hotel getHotelForRatingFallback(String hotelId, Throwable throwable) {
        System.err.println("Fallback triggered for hotel ID " + hotelId + ": " + throwable.getMessage());
        return null;
    }
}
