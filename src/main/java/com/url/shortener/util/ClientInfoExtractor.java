package com.url.shortener.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ClientInfoExtractor {

    public ClientInfo extract(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return ClientInfo.builder()
            .browser(parseBrowser(userAgent))
            .operatingSystem(parseOperatingSystem(userAgent))
            .ipAddress(resolveIpAddress(request))
            .country("Unknown")
            .userAgent(userAgent)
            .deviceInfo(userAgent == null ? "Unknown Device" : userAgent)
            .build();
    }

    private String resolveIpAddress(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private String parseBrowser(String userAgent) {
        if (userAgent == null || userAgent.isBlank()) {
            return "Unknown";
        }
        if (userAgent.contains("Edg/")) {
            return "Edge";
        }
        if (userAgent.contains("Chrome/")) {
            return "Chrome";
        }
        if (userAgent.contains("Firefox/")) {
            return "Firefox";
        }
        if (userAgent.contains("Safari/") && !userAgent.contains("Chrome/")) {
            return "Safari";
        }
        return "Unknown";
    }

    private String parseOperatingSystem(String userAgent) {
        if (userAgent == null || userAgent.isBlank()) {
            return "Unknown";
        }
        if (userAgent.contains("Windows")) {
            return "Windows";
        }
        if (userAgent.contains("Mac OS X")) {
            return "macOS";
        }
        if (userAgent.contains("Linux")) {
            return "Linux";
        }
        if (userAgent.contains("Android")) {
            return "Android";
        }
        if (userAgent.contains("iPhone") || userAgent.contains("iPad")) {
            return "iOS";
        }
        return "Unknown";
    }
}
