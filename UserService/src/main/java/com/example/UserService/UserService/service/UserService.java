package com.example.UserService.UserService.service;

import com.example.UserService.UserService.entity.Hotel;
import com.example.UserService.UserService.entity.Rating;
import com.example.UserService.UserService.entity.User;
import com.example.UserService.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        String randomUserID = UUID.randomUUID().toString();
        user.setUserId(randomUserID);
        return userRepository.save(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUser(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional == null) {
            return null;
        }

        User user = userOptional.get();

        Rating[] ratingOfUsers = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);

        List<Rating> ratings = Arrays.stream(ratingOfUsers).toList();
        List<Rating> ratingList = ratings.stream().map(rating -> {

            Hotel hotel = restTemplate.getForObject(
                    "http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), // Corrected URL
                    Hotel.class
            );

            rating.setHotel(hotel);

            return rating;

        }).collect(Collectors.toList());

        return userOptional.orElse(null);
    }
}
