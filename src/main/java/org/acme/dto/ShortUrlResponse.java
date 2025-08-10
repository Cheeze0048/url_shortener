package org.acme.dto;
import java.time.LocalDateTime;

// Response DTO for a created short URL
public record ShortUrlResponse(
        Long id,
        String originalUrl,
        String shortCode,
        String shortUrl,
        String description,
        LocalDateTime createdAt,
        Long clickCount,
        Boolean active
) {}
