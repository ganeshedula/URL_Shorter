package com.url.shortener.repo;

import com.url.shortener.models.ClickEvent;
import com.url.shortener.models.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ClickEventRepository extends JpaRepository<ClickEvent,Long> {
    List<ClickEvent> findByUrlMappingAndTimestampBetween(UrlMapping urlMapping, LocalDateTime startDate, LocalDateTime endDate);
    List<ClickEvent> findByUrlMappingInAndTimestampBetween(List<UrlMapping> urlMapping, LocalDateTime startDate, LocalDateTime endDate);

}
