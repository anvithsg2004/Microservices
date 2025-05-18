package com.example.HotelService.HotelService.feignClient;

import com.example.HotelService.HotelService.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "USER-SERVICE")
public interface UserService {

    @PostMapping("/users")
    ResponseEntity<User> createUser(@RequestBody User user);

    @GetMapping("/users")
    ResponseEntity<List<User>> getAllUsers();

    @GetMapping("/users/{uid}")
    ResponseEntity<User> getUserById(@PathVariable("uid") String uid);

    @PutMapping("/users/{uid}")
    ResponseEntity<User> updateUser(@PathVariable("uid") String uid, @RequestBody User user);

    @DeleteMapping("/users/{uid}")
    ResponseEntity<Void> deleteUser(@PathVariable("uid") String uid);
}
