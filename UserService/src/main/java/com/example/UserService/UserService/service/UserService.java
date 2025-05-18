package com.example.UserService.UserService.service;

import com.example.UserService.UserService.entity.Hotel;
import com.example.UserService.UserService.entity.Rating;
import com.example.UserService.UserService.entity.User;
import com.example.UserService.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + uid));

        //Now we have got the name, email and about of the user.
        //Now we will fetch the user ratings of the given user id with help of the rating service.
        // Fetch user ratings from RatingService
        String ratingServiceUrl = "http://RATING-SERVICE/ratings/user/" + uid;
        try {
            Rating[] ratingsArray = restTemplate.getForObject(ratingServiceUrl, Rating[].class);
            List<Rating> ratings = ratingsArray != null ? List.of(ratingsArray) : List.of();

            // Fetch hotel details for each rating
            for (Rating rating : ratings) {
                String hotelServiceUrl = "http://HOTEL-SERVICE/hotels/" + rating.getHotelId();
                try {
                    Hotel hotel = restTemplate.getForObject(hotelServiceUrl, Hotel.class);
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
