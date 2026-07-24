package com.url.shortener.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
    name = "url_mappings",
    indexes = {
        @Index(name = "idx_url_mapping_short_code", columnList = "short_code", unique = true),
        @Index(name = "idx_url_mapping_user_created_at", columnList = "user_id,created_at"),
        @Index(name = "idx_url_mapping_original_url", columnList = "original_url")
    }
)
public class UrlMapping extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;

    @Column(name = "short_code", nullable = false, unique = true, length = 20)
    private String shortCode;

    @Column(name = "click_count", nullable = false)
    private long clickCount;

    @Column(name = "last_accessed_at")
    private OffsetDateTime lastAccessedAt;

    @Column(name = "expiration_date")
    private OffsetDateTime expirationDate;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "urlMapping", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClickEvent> clickEvents = new ArrayList<>();
}
