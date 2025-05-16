package com.example.tenpoChallenge.service.impl;

import com.example.tenpoChallenge.dto.PercentageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PercentageServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private PercentageServiceImpl percentageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debe obtener el porcentaje desde el servicio externo")
    void getPercentage_Success() {
        double expected = 0.1;
        PercentageResponse mockResponse = new PercentageResponse();
        mockResponse.setPercentage(expected);

        when(restTemplate.getForEntity(anyString(), eq(PercentageResponse.class)))
                .thenReturn(ResponseEntity.ok(mockResponse));

        double result = percentageService.getPercentage();

        assertEquals(expected, result);
        verify(restTemplate).getForEntity(anyString(), eq(PercentageResponse.class));
    }

    @Test
    @DisplayName("Debe devolver valor cacheado si falla el servicio")
    void getPercentage_FallbackToCache() {
        when(restTemplate.getForEntity(anyString(), eq(PercentageResponse.class)))
                .thenThrow(new RuntimeException("Fallo externo"));

        when(cacheManager.getCache("percentageCache")).thenReturn(cache);
        when(cache.get(org.springframework.cache.interceptor.SimpleKey.EMPTY, Double.class)).thenReturn(0.15);

        double result = percentageService.getPercentage();

        assertEquals(0.15, result);
        verify(cache).get(org.springframework.cache.interceptor.SimpleKey.EMPTY, Double.class);
    }

    @Test
    @DisplayName("Debe lanzar excepción si falla el servicio y no hay cache")
    void getPercentage_NoServiceNoCache() {
        when(restTemplate.getForEntity(anyString(), eq(PercentageResponse.class)))
                .thenThrow(new RuntimeException("Fallo externo"));

        when(cacheManager.getCache("percentageCache")).thenReturn(cache);
        when(cache.get(org.springframework.cache.interceptor.SimpleKey.EMPTY, Double.class)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            percentageService.getPercentage();
        });

        assertTrue(exception.getMessage().contains("No percentage available"));
    }
}
