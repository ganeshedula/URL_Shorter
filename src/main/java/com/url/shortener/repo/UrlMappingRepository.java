package com.url.shortener.repo;

import com.url.shortener.models.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, UUID>, JpaSpecificationExecutor<UrlMapping> {
    Optional<UrlMapping> findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);
}
