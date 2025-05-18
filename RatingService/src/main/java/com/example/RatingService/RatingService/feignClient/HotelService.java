package com.example.RatingService.RatingService.feignClient;

import com.example.RatingService.RatingService.entity.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

    @PostMapping("/hotels")
    ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel);

    @GetMapping("/hotels")
    ResponseEntity<List<Hotel>> getAllHotels();

    @GetMapping("/hotels/{uid}")
    ResponseEntity<Hotel> getHotelById(@PathVariable("uid") String uid);

    @PutMapping("/hotels/{uid}")
    ResponseEntity<Hotel> updateHotel(@PathVariable("uid") String uid, @RequestBody Hotel hotel);

    @DeleteMapping("/hotels/{uid}")
    ResponseEntity<Void> deleteHotel(@PathVariable("uid") String uid);
}
