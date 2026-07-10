package com.url.shortener.security;

import com.url.shortener.security.jwt.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityBeansConfig {

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }
}

