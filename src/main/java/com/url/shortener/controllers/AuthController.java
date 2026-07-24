package com.url.shortener.controllers;

import com.url.shortener.dtos.ApiResponse;
import com.url.shortener.dtos.AuthResponse;
import com.url.shortener.dtos.LoginRequest;
import com.url.shortener.dtos.RefreshTokenRequest;
import com.url.shortener.dtos.RegisterRequest;
import com.url.shortener.dtos.UserResponse;
import com.url.shortener.service.AuthService;
import com.url.shortener.util.ClientInfoExtractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;
    private final ClientInfoExtractor clientInfoExtractor;

    public AuthController(AuthService authService, ClientInfoExtractor clientInfoExtractor) {
        this.authService = authService;
        this.clientInfoExtractor = clientInfoExtractor;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
        @Valid @RequestBody RegisterRequest request,
        HttpServletRequest httpServletRequest
    ) {
        AuthResponse response = authService.register(request, clientInfoExtractor.extract(httpServletRequest));
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", response));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user and issue JWT tokens")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
        @Valid @RequestBody LoginRequest request,
        HttpServletRequest httpServletRequest
    ) {
        AuthResponse response = authService.login(request, clientInfoExtractor.extract(httpServletRequest));
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token and rotate refresh token")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(
        @Valid @RequestBody RefreshTokenRequest request,
        HttpServletRequest httpServletRequest
    ) {
        AuthResponse response = authService.refresh(request, clientInfoExtractor.extract(httpServletRequest));
        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", response));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout current session")
    public ResponseEntity<ApiResponse<Void>> logout(
        @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
        @Valid @RequestBody(required = false) RefreshTokenRequest request
    ) {
        authService.logout(extractAccessToken(authorizationHeader), request == null ? null : request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success("Logout successful", null));
    }

    @PostMapping("/logout-all")
    @Operation(summary = "Logout all sessions for the current user")
    public ResponseEntity<ApiResponse<Void>> logoutAll(
        @RequestHeader("Authorization") String authorizationHeader
    ) {
        authService.logoutAll(extractAccessToken(authorizationHeader));
        return ResponseEntity.ok(ApiResponse.success("All sessions logged out successfully", null));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current authenticated user")
    public ResponseEntity<ApiResponse<UserResponse>> me() {
        return ResponseEntity.ok(ApiResponse.success("Current user fetched successfully", authService.currentUser()));
    }

    private String extractAccessToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7);
    }
}
