package com.example.HotelService.HotelService.controller;

import com.example.HotelService.HotelService.entity.Hotel;
import com.example.HotelService.HotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    // Create hotel
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel createdHotel = hotelService.createHotel(hotel);
        return ResponseEntity.ok(createdHotel);
    }

    // Get all hotels
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    // Get hotel by ID
    @GetMapping("/{uid}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String uid) {
        Hotel hotel = hotelService.getHotelById(uid);
        return ResponseEntity.ok(hotel);
    }

    // Update hotel
    @PutMapping("/{uid}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable String uid, @RequestBody Hotel hotel) {
        Hotel updatedHotel = hotelService.updateHotel(uid, hotel);
        return ResponseEntity.ok(updatedHotel);
    }

    // Delete hotel
    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String uid) {
        hotelService.deleteHotel(uid);
        return ResponseEntity.noContent().build();
    }
}
