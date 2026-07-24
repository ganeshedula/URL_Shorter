package com.url.shortener.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CreateShortUrlRequest {

    @NotBlank(message = "URL is required")
    @Pattern(
        regexp = "^(https?://).+",
        message = "URL must start with http:// or https://"
    )
    private String url;

    private OffsetDateTime expirationDate;
}
