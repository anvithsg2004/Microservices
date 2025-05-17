package com.example.RatingService.RatingService.service;

import com.example.RatingService.RatingService.entity.Rating;
import com.example.RatingService.RatingService.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    // Create Rating
    public Rating createRating(Rating rating) {
        rating.setRatingId(UUID.randomUUID().toString());
        return ratingRepository.save(rating);
    }

    // Update Rating
    public Rating updateRating(String ratingId, Rating updatedRating) {
        Optional<Rating> existingRatingOpt = ratingRepository.findById(ratingId);
        if (existingRatingOpt.isPresent()) {
            Rating existingRating = existingRatingOpt.get();
            existingRating.setUserId(updatedRating.getUserId());
            existingRating.setHotelId(updatedRating.getHotelId());
            existingRating.setFeedback(updatedRating.getFeedback());
            existingRating.setRating(updatedRating.getRating());
            return ratingRepository.save(existingRating);
        } else {
            throw new RuntimeException("Rating not found with id: " + ratingId);
        }
    }

    // Delete Rating
    public void deleteRating(String ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    // Get All Ratings
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    // Get Rating by ID
    public Rating getRatingById(String ratingId) {
        return ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id: " + ratingId));
    }

    // Get Ratings by User ID
    public List<Rating> getRatingsByUserId(String userId) {
        List<Rating> ratings = ratingRepository.findByUserId(userId);
        if (ratings.isEmpty()) {
            throw new RuntimeException("No ratings found for user id: " + userId);
        }
        return ratings;
    }

    // Get Ratings by Hotel ID
    public List<Rating> getRatingsByHotelId(String hotelId) {
        List<Rating> ratings = ratingRepository.findByHotelId(hotelId);
        if (ratings.isEmpty()) {
            throw new RuntimeException("No ratings found for hotel id: " + hotelId);
        }
        return ratings;
    }
}
