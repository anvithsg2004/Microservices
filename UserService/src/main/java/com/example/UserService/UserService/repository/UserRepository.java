package com.example.UserService.UserService.repository;

import com.example.UserService.UserService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
