package com.example.HotelService.HotelService.respositories;

import com.example.HotelService.HotelService.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, String> {
}
