package org.acme.dto;
// Update DTO
public record UpdateUrlRequest(
        String description,
        Boolean active
) {}
