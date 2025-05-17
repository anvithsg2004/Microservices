package com.example.HotelService.HotelService.service;

import com.example.HotelService.HotelService.entity.Hotel;
import com.example.HotelService.HotelService.respositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    // Create a new hotel
    public Hotel create(Hotel hotel) {
        // Generate random UUID for hotel ID if not set
        hotel.setId(UUID.randomUUID().toString());
        return hotelRepository.save(hotel);
    }

    // Get all hotels
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    // Get single hotel by ID
    public Hotel get(String id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        return optionalHotel.orElse(null); // or throw custom exception
    }

}
