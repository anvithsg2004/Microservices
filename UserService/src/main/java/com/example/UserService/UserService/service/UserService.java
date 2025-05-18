package com.example.UserService.UserService.service;

import com.example.UserService.UserService.entity.Hotel;
import com.example.UserService.UserService.entity.Rating;
import com.example.UserService.UserService.entity.User;
import com.example.UserService.UserService.feignClient.HotelService;
import com.example.UserService.UserService.feignClient.RatingService;
import com.example.UserService.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

        // For each user, fetch their ratings and hotel details
        for (User user : users) {
            try {
                // Fetch ratings from RatingService
                ResponseEntity<List<Rating>> ratingsResponse = ratingService.getRatingsByUserId(user.getUid());
                List<Rating> ratings = ratingsResponse.getBody() != null ? ratingsResponse.getBody() : List.of();

                // Fetch hotel details for each rating
                for (Rating rating : ratings) {
                    try {
                        ResponseEntity<Hotel> hotelResponse = hotelService.getHotelById(rating.getHotelId());
                        Hotel hotel = hotelResponse.getBody();
                        rating.setHotel(hotel);
                    } catch (Exception e) {
                        rating.setHotel(null); // Handle HotelService failure gracefully
                    }
                }

                user.setRatings(ratings);
            } catch (Exception e) {
                user.setRatings(List.of()); // Handle RatingService failure gracefully
            }
        }

        return users;
    }

    // Get User by ID
    public User getUserById(String uid) {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + uid));

        // Fetch user ratings from RatingService using Feign Client
        try {
            ResponseEntity<List<Rating>> ratingsResponse = ratingService.getRatingsByUserId(uid);
            List<Rating> ratings = ratingsResponse.getBody() != null ? ratingsResponse.getBody() : List.of();

            // Fetch hotel details for each rating using Feign Client
            for (Rating rating : ratings) {
                try {
                    ResponseEntity<Hotel> hotelResponse = hotelService.getHotelById(rating.getHotelId());
                    Hotel hotel = hotelResponse.getBody();
                    rating.setHotel(hotel);
                } catch (Exception e) {
                    rating.setHotel(null); // Handle HotelService failure gracefully
                }
            }

            user.setRatings(ratings);
        } catch (Exception e) {
            user.setRatings(List.of()); // Handle RatingService failure gracefully
        }

        return user;
    }
}
