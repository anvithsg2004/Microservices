package com.example.RatingService.RatingService.service;

import com.example.RatingService.RatingService.entity.Rating;
import com.example.RatingService.RatingService.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository repository;

    public Rating create(Rating rating) {
        return repository.save(rating);
    }

    public List<Rating> getRatings() {
        return repository.findAll();
    }

    public List<Rating> getRatingByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public List<Rating> getRatingByHotelId(String hotelId) {
        return repository.findByHotelId(hotelId);
    }

}
