package com.url.shortener.util;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientInfo {
    private final String browser;
    private final String operatingSystem;
    private final String ipAddress;
    private final String country;
    private final String userAgent;
    private final String deviceInfo;
}
