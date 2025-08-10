package org.acme.entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Entity
@Table(name = "short_urls", indexes = {
        @Index(name = "idx_short_code", columnList = "shortCode", unique = true),
        @Index(name = "idx_created_at", columnList = "createdAt")
})
public class ShortUrl extends PanacheEntity {

    @NotBlank(message = "Original URL cannot be blank")
    @Column(nullable = false, length = 2048)
    public String originalUrl;

    @NotBlank(message = "Short code cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,10}$", message = "Short code must be 6-10 alphanumeric characters")
    @Column(nullable = false, unique = true, length = 10)
    public String shortCode;

    @Column(nullable = false)
    public LocalDateTime createdAt;

    @Column
    public LocalDateTime lastAccessedAt;

    @Column(nullable = false)
    public Long clickCount = 0L;

    @Column(length = 500)
    public String description;

    @Column(nullable = false)
    public Boolean active = true;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public static ShortUrl findByShortCode(String shortCode) {
        return find("shortCode", shortCode).firstResult();
    }

    public static ShortUrl findActiveByShortCode(String shortCode) {
        return find("shortCode = ?1 and active = ?2", shortCode, true).firstResult();
    }

    public void incrementClickCount() {
        this.clickCount++;
        this.lastAccessedAt = LocalDateTime.now();
    }
}
