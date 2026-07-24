package com.url.shortener.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshSession {
    private String sessionId;
    private UUID userId;
    private String email;
    private String refreshToken;
    private OffsetDateTime loginAt;
    private OffsetDateTime expiresAt;
    private String deviceInfo;
    private String ipAddress;
}
