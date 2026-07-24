package com.url.shortener.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
public class ShortUrlResponse {
    private final UUID id;
    private final String shortCode;
    private final String shortUrl;
    private final String originalUrl;
    private final long clickCount;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;
    private final OffsetDateTime expirationDate;
    private final OffsetDateTime lastAccessedAt;
    private final boolean active;
}
