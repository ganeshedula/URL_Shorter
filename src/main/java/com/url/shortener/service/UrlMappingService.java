package com.url.shortener.service;

import com.url.shortener.dtos.ClickEventDto;
import com.url.shortener.dtos.UrlMappingDTO;
import com.url.shortener.models.UrlMapping;
import com.url.shortener.models.User;
import com.url.shortener.repo.ClickEventRepository;
import com.url.shortener.repo.UrlMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {

    private UrlMappingRepository urlMappingRepository;
    private ClickEventRepository clickEventRepository;
    public UrlMappingDTO createShortUrl(String originalUrl, User user) {
        String shortUrl = generateShortUrl();
        UrlMapping urlMapping = new UrlMapping();

        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setCreatedAt(LocalDateTime.now());
        urlMapping.setUser(user);
        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
        return convertToDTO(savedUrlMapping);
    }

    private UrlMappingDTO convertToDTO(UrlMapping urlMapping) {
        UrlMappingDTO dto = new UrlMappingDTO();
        dto.setId(urlMapping.getId());
        dto.setOriginalUrl(urlMapping.getOriginalUrl());
        dto.setShortUrl(urlMapping.getShortUrl());
        dto.setClickCount(urlMapping.getClickCount());
        dto.setCreatedAt(urlMapping.getCreatedAt());
        dto.setUsername(urlMapping.getUser().getUsername());
        return dto;
    }

    private String generateShortUrl() {
        // Implement short URL generation logic here
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder(8);

        for(int i = 0; i < 8; i++) {
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }

        return shortUrl.toString();
    }

    public List<UrlMappingDTO> getUrlsByUser(User user) {
        return urlMappingRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<ClickEventDto> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMapping != null) {
            return clickEventRepository.findByUrlMappingAndTimestampBetween(urlMapping, start, end).stream()
                    .collect(Collectors.groupingBy(click -> click.getTimestamp().toLocalDate(), Collectors.counting())
                    ).entrySet().stream()
                    .map(entry ->{
                        ClickEventDto clickEventDto = new ClickEventDto();
                        clickEventDto.setClickDate(entry.getKey().atStartOfDay());
                        clickEventDto.setCount(entry.getValue());
                        return clickEventDto;
                    })
                    .collect(Collectors.toList());
        }
        return new java.util.ArrayList<>();
    }
}
