package org.acme.dto;

import java.time.LocalDateTime;

// Statistics DTO
public record UrlStatistics(
        String shortCode,
        String originalUrl,
        Long clickCount,
        LocalDateTime createdAt,
        LocalDateTime lastAccessedAt,
        Boolean active
) {}
