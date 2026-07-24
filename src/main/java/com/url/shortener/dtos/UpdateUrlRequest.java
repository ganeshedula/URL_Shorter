package com.url.shortener.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class UpdateUrlRequest {

    @Pattern(
        regexp = "^(https?://).+",
        message = "URL must start with http:// or https://"
    )
    private String url;

    private OffsetDateTime expirationDate;
    private Boolean active;
}
