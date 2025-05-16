package com.example.tenpoChallenge.service.impl;

import com.example.tenpoChallenge.dto.CalculationRequestDTO;
import com.example.tenpoChallenge.dto.CalculationResponseDTO;
import com.example.tenpoChallenge.exception.CalculationException;
import com.example.tenpoChallenge.model.CallLog;
import com.example.tenpoChallenge.service.CallLogService;
import com.example.tenpoChallenge.service.PercentageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculationServiceImplTest {

    @Mock
    private PercentageService percentageService;

    @Mock
    private CallLogService callLogService;

    @InjectMocks
    private CalculationServiceImpl calculationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debería calcular correctamente con porcentaje")
    void testCalculateWithPercentage_Success() {
        CalculationRequestDTO request = new CalculationRequestDTO();
        request.setNum1(100.0);
        request.setNum2(50.0);

        when(percentageService.getPercentage()).thenReturn(0.1);

        CalculationResponseDTO response = calculationService.calculateWithPercentage(request);

        assertEquals(165.0, response.getResult());
        assertEquals("Cálculo realizado con éxito", response.getMessage());

        verify(callLogService).logCall(eq("/api/calculate"), anyString(), eq("165.0"), isNull());
    }

    @Test
    @DisplayName("Debería lanzar CalculationException si falla el servicio de porcentaje")
    void testCalculateWithPercentage_Failure() {
        CalculationRequestDTO request = new CalculationRequestDTO();
        request.setNum1(10.0);
        request.setNum2(20.0);

        when(percentageService.getPercentage()).thenThrow(new RuntimeException("Servicio caído"));

        CalculationException exception = assertThrows(
                CalculationException.class,
                () -> calculationService.calculateWithPercentage(request)
        );

        assertTrue(exception.getMessage().contains("Error durante el cálculo"));
        verify(callLogService).logCall(eq("/api/calculate"), anyString(), isNull(), contains("Servicio caído"));
    }

    @Test
    @DisplayName("Debería retornar historial de logs paginado")
    void testGetCallHistory() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<CallLog> mockPage = new PageImpl<>(Collections.emptyList());

        when(callLogService.getLogs(pageRequest)).thenReturn(mockPage);

        Page<CallLog> result = calculationService.getCallHistory(0, 5);

        assertEquals(mockPage, result);
        verify(callLogService).getLogs(pageRequest);
    }
}
