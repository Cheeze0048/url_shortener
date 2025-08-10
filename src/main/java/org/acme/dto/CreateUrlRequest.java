package org.acme.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// Request DTO for creating a short URL
public record CreateUrlRequest(
        @NotBlank(message = "URL cannot be blank")
        @Pattern(regexp = "^(https?://).*", message = "URL must start with http:// or https://")
        String url,

        String description,

        @Pattern(regexp = "^[a-zA-Z0-9]{6,10}$", message = "Custom code must be 6-10 alphanumeric characters")
        String customCode
) {}

