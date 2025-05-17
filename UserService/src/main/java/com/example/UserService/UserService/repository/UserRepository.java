package com.example.UserService.UserService.repository;

import com.example.UserService.UserService.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
