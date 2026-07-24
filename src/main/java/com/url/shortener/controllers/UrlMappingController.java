package com.url.shortener.controllers;

import com.url.shortener.dtos.ClickEventDto;
import com.url.shortener.dtos.UrlMappingDTO;
import com.url.shortener.models.User;
import com.url.shortener.service.UrlMappingService;
import com.url.shortener.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/url")
@AllArgsConstructor
public class UrlMappingController {
    private UrlMappingService urlMappingService;
    private UserService userService;

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingDTO> shortenUrl(@RequestBody Map<String, String> request, Principal principal){

        String originalUrl = request.get("originalUrl");
        User user = userService.findByusername(principal.getName());
        UrlMappingDTO urlMappingDTO = urlMappingService.createShortUrl(originalUrl,user);

        return ResponseEntity.ok(urlMappingDTO);
    }

    @GetMapping("/myurls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Iterable<UrlMappingDTO>> getMyUrls(Principal principal) {
        User user = userService.findByusername(principal.getName());
        List<UrlMappingDTO> urlMappings = urlMappingService.getUrlsByUser(user);
        return ResponseEntity.ok(urlMappings);
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventDto>> getUrlAnalytics(@PathVariable String shortUrl,
                                                               @RequestParam("startDate") String startDate,
                                                               @RequestParam("endDate") String endDate){

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        List<ClickEventDto> clickEventDTOS =  urlMappingService.getClickEventsByDate(shortUrl,start,end);
        return ResponseEntity.ok(clickEventDTOS);
    }
}