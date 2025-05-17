package com.example.HotelService.HotelService.repository;

import com.example.HotelService.HotelService.entity.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotelRepository extends MongoRepository<Hotel, String> {
}
