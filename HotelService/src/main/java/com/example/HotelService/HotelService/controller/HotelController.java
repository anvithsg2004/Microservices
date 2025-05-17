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

    // Create a new hotel
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel savedHotel = hotelService.create(hotel);
        return ResponseEntity.status(201).body(savedHotel); // HTTP 201 Created
    }

    // Get all hotels
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAll();
        return ResponseEntity.ok(hotels); // HTTP 200 OK
    }

    // Get hotel by ID
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String id) {
        Hotel hotel = hotelService.get(id);
        if (hotel == null) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found
        }
        return ResponseEntity.ok(hotel); // HTTP 200 OK
    }
}
