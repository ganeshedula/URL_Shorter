package com.url.shortener.dtos;

import com.url.shortener.models.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
public class UserResponse {
    private final UUID id;
    private final String email;
    private final String username;
    private final Role role;
    private final OffsetDateTime createdAt;
}
