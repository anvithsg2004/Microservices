package com.example.HotelService.HotelService.feignClient;

import com.example.HotelService.HotelService.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    @PostMapping("/ratings")
    ResponseEntity<Rating> createRating(@RequestBody Rating rating);

    @GetMapping("/ratings")
    ResponseEntity<List<Rating>> getAllRatings();

    @GetMapping("/ratings/{ratingId}")
    ResponseEntity<Rating> getRatingById(@PathVariable("ratingId") String ratingId);

    @GetMapping("/ratings/user/{userId}")
    ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable("userId") String userId);

    @GetMapping("/ratings/hotel/{hotelId}")
    ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable("hotelId") String hotelId);

    @PutMapping("/ratings/{ratingId}")
    ResponseEntity<Rating> updateRating(@PathVariable("ratingId") String ratingId, @RequestBody Rating rating);

    @DeleteMapping("/ratings/{ratingId}")
    ResponseEntity<Void> deleteRating(@PathVariable("ratingId") String ratingId);
}
