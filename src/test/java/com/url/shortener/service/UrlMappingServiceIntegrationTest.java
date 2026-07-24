package com.url.shortener.service;

import com.url.shortener.config.AppProperties;
import com.url.shortener.dtos.PagedResponse;
import com.url.shortener.dtos.ShortUrlResponse;
import com.url.shortener.models.Role;
import com.url.shortener.models.User;
import com.url.shortener.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UrlMappingServiceIntegrationTest {

    @Autowired
    private UrlMappingService urlMappingService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private org.springframework.data.redis.core.StringRedisTemplate stringRedisTemplate;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        user = new User();
        user.setEmail("owner@example.com");
        user.setPassword("secret");
        user.setRole(Role.ROLE_USER);
        user = userRepository.save(user);
    }

    @Test
    void createShortUrlPersistsUrlAndReturnsPublicLink() {
        ShortUrlResponse response = urlMappingService.createShortUrl("https://google.com", OffsetDateTime.now().plusDays(1), user);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getShortCode()).hasSize(8);
        assertThat(response.getShortUrl()).contains("/" + response.getShortCode());
        assertThat(response.getOriginalUrl()).isEqualTo("https://google.com");
    }

    @Test
    void getUrlsByUserReturnsPaginatedResults() {
        urlMappingService.createShortUrl("https://example.com/one", null, user);
        urlMappingService.createShortUrl("https://example.com/two", null, user);

        PagedResponse<ShortUrlResponse> response = urlMappingService.getUrlsByUser(user, 0, 10, "createdAt", "desc", "example");

        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getTotalElements()).isEqualTo(2);
    }
}
