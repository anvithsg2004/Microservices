package com.example.HotelService.HotelService.service;

import com.example.HotelService.HotelService.entity.Hotel;
import com.example.HotelService.HotelService.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    // Create Hotel
    public Hotel createHotel(Hotel hotel) {
        hotel.setUid(UUID.randomUUID().toString());
        return hotelRepository.save(hotel);
    }

    // Update Hotel
    public Hotel updateHotel(String uid, Hotel updatedHotel) {
        Optional<Hotel> existingHotelOpt = hotelRepository.findById(uid);
        if (existingHotelOpt.isPresent()) {
            Hotel existingHotel = existingHotelOpt.get();
            existingHotel.setName(updatedHotel.getName());
            existingHotel.setLocation(updatedHotel.getLocation());
            existingHotel.setAbout(updatedHotel.getAbout());
            return hotelRepository.save(existingHotel);
        } else {
            throw new RuntimeException("Hotel not found with id: " + uid);
        }
    }

    // Delete Hotel
    public void deleteHotel(String uid) {
        hotelRepository.deleteById(uid);
    }

    // Get All Hotels
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    // Get Hotel by ID
    public Hotel getHotelById(String uid) {
        return hotelRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + uid));
    }
}
