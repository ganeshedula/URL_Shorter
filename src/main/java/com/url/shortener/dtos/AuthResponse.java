package com.url.shortener.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private final String accessToken;
    private final String refreshToken;
    private final String tokenType;
    private final long accessTokenExpiresInSeconds;
    private final UserResponse user;
}
