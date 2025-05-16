package com.example.tenpoChallenge.service.impl;

import com.example.tenpoChallenge.dto.PercentageResponse;
import com.example.tenpoChallenge.service.PercentageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PercentageServiceImpl implements PercentageService {

    private final RestTemplate restTemplate;
    private final CacheManager cacheManager;

    @Override
    @Cacheable(value = "percentageCache", unless = "#result == null")
    public double getPercentage() {
        try {
            log.info("Attempting to fetch percentage from external service");
            ResponseEntity<PercentageResponse> response = restTemplate.getForEntity("http://tenpo-mock-service:8080/percentage", PercentageResponse.class);
            assert response.getBody() != null;
            double percentage = response.getBody().getPercentage();
            log.info("Successfully retrieved percentage: {}", percentage);
            return percentage;
        } catch (Exception e) {
            log.error("Failed to fetch percentage from external service", e);
            Cache cache = cacheManager.getCache("percentageCache");
            if (cache != null) {
                Double cached = cache.get(SimpleKey.EMPTY, Double.class);
                if (cached != null) {
                    log.warn("Returning cached percentage: {}", cached);
                    return cached;
                }
            }
            log.error("No cached percentage available. Failing the request.");
            throw new RuntimeException("No percentage available and external service failed.");
        }
    }
}
