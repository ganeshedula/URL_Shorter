package com.url.shortener.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ShortCodeGenerator {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int DEFAULT_LENGTH = 8;
    private final SecureRandom secureRandom = new SecureRandom();

    public String generate() {
        StringBuilder builder = new StringBuilder(DEFAULT_LENGTH);
        for (int index = 0; index < DEFAULT_LENGTH; index++) {
            builder.append(CHARACTERS.charAt(secureRandom.nextInt(CHARACTERS.length())));
        }
        return builder.toString();
    }
}
