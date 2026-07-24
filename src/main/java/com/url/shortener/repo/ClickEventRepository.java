package com.url.shortener.repo;

import com.url.shortener.models.ClickEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClickEventRepository extends JpaRepository<ClickEvent, UUID> {
    List<ClickEvent> findTop20ByUrlMapping_IdOrderByAccessedAtDesc(UUID urlMappingId);
    List<ClickEvent> findByUrlMapping_IdOrderByAccessedAtAsc(UUID urlMappingId);
}
