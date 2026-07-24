package com.url.shortener.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class ClickEventDto {
    private final OffsetDateTime accessedAt;
    private final String browser;
    private final String operatingSystem;
    private final String ipAddress;
    private final String country;
}
