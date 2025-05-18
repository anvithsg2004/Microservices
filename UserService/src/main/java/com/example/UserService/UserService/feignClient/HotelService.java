package com.example.UserService.UserService.feignClient;

import com.example.UserService.UserService.entity.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

    @GetMapping("/hotels/{uid}")
    Hotel getHotelById(@PathVariable("uid") String uid);
}
