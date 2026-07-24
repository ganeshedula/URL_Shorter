package com.url.shortener.service;

import com.url.shortener.dtos.UserResponse;
import com.url.shortener.exception.UserNotFoundException;
import com.url.shortener.models.User;
import com.url.shortener.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found for email: " + email));
    }

    public User findById(UUID userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found for id: " + userId));
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .username(user.getUsername())
            .role(user.getRole())
            .createdAt(OffsetDateTime.ofInstant(user.getCreatedAt(), ZoneOffset.UTC))
            .build();
    }
}
