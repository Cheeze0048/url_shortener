package org.acme.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.BadRequestException;
import org.acme.dto.CreateUrlRequest;
import org.acme.dto.ShortUrlResponse;
import org.acme.dto.UpdateUrlRequest;
import org.acme.dto.UrlStatistics;
import org.acme.entity.ShortUrl;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UrlShortenerService {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int DEFAULT_CODE_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    @ConfigProperty(name = "app.base-url", defaultValue = "http://localhost:8080")
    String baseUrl;

    @Transactional
    public ShortUrlResponse createShortUrl(CreateUrlRequest request) {
        // Validate URL format
        if (!isValidUrl(request.url())) {
            throw new BadRequestException("Invalid URL format");
        }

        // Generate or validate custom code
        String shortCode;
        if (request.customCode() != null && !request.customCode().isEmpty()) {
            // Check if custom code is already taken
            if (ShortUrl.findByShortCode(request.customCode()) != null) {
                throw new BadRequestException("Custom code already exists");
            }
            shortCode = request.customCode();
        } else {
            // Generate unique short code
            shortCode = generateUniqueShortCode();
        }

        // Create and persist entity
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.originalUrl = request.url();
        shortUrl.shortCode = shortCode;
        shortUrl.description = request.description();
        shortUrl.persist();

        return mapToResponse(shortUrl);
    }

    @Transactional
    public String getOriginalUrl(String shortCode) {
        ShortUrl shortUrl = ShortUrl.findActiveByShortCode(shortCode);
        if (shortUrl == null) {
            throw new NotFoundException("Short URL not found or inactive");
        }

        // Increment click count
        shortUrl.incrementClickCount();
        shortUrl.persist();

        return shortUrl.originalUrl;
    }

    public ShortUrlResponse getShortUrlDetails(String shortCode) {
        ShortUrl shortUrl = ShortUrl.findByShortCode(shortCode);
        if (shortUrl == null) {
            throw new NotFoundException("Short URL not found");
        }
        return mapToResponse(shortUrl);
    }

    public UrlStatistics getStatistics(String shortCode) {
        ShortUrl shortUrl = ShortUrl.findByShortCode(shortCode);
        if (shortUrl == null) {
            throw new NotFoundException("Short URL not found");
        }

        return new UrlStatistics(
                shortUrl.shortCode,
                shortUrl.originalUrl,
                shortUrl.clickCount,
                shortUrl.createdAt,
                shortUrl.lastAccessedAt,
                shortUrl.active
        );
    }

    @Transactional
    public ShortUrlResponse updateShortUrl(String shortCode, UpdateUrlRequest request) {
        ShortUrl shortUrl = ShortUrl.findByShortCode(shortCode);
        if (shortUrl == null) {
            throw new NotFoundException("Short URL not found");
        }

        if (request.description() != null) {
            shortUrl.description = request.description();
        }
        if (request.active() != null) {
            shortUrl.active = request.active();
        }

        shortUrl.persist();
        return mapToResponse(shortUrl);
    }

    @Transactional
    public void deleteShortUrl(String shortCode) {
        ShortUrl shortUrl = ShortUrl.findByShortCode(shortCode);
        if (shortUrl == null) {
            throw new NotFoundException("Short URL not found");
        }
        shortUrl.delete();
    }

    public List<ShortUrlResponse> listAllUrls(int page, int size) {
        List<ShortUrl> urls = ShortUrl.findAll()
                .page(page, size)
                .list();

        return urls.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private String generateUniqueShortCode() {
        String code;
        int attempts = 0;
        do {
            code = generateShortCode(DEFAULT_CODE_LENGTH);
            attempts++;
            if (attempts > 10) {
                // If too many collisions, increase code length
                code = generateShortCode(DEFAULT_CODE_LENGTH + 1);
            }
        } while (ShortUrl.findByShortCode(code) != null);

        return code;
    }

    private String generateShortCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    private boolean isValidUrl(String url) {
        return url != null &&
                (url.startsWith("http://") || url.startsWith("https://")) &&
                url.length() > 10;
    }

    private ShortUrlResponse mapToResponse(ShortUrl entity) {
        return new ShortUrlResponse(
                entity.id,
                entity.originalUrl,
                entity.shortCode,
                baseUrl + "/s/" + entity.shortCode,
                entity.description,
                entity.createdAt,
                entity.clickCount,
                entity.active
        );
    }
}
