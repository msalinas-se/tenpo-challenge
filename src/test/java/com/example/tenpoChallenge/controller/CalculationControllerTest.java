package com.example.tenpoChallenge.controller;

import com.example.tenpoChallenge.dto.CalculationRequestDTO;
import com.example.tenpoChallenge.dto.CalculationResponseDTO;
import com.example.tenpoChallenge.model.CallLog;
import com.example.tenpoChallenge.service.CalculationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculationController.class)
class CalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculationService calculationService;

    @Test
    @DisplayName("POST /api/calculate debe retornar el resultado correctamente")
    void calculate_RetornaResultadoCorrecto() throws Exception {
        CalculationRequestDTO request = new CalculationRequestDTO();
        request.setNum1(100.0);
        request.setNum2(50.0);

        CalculationResponseDTO expectedResponse = new CalculationResponseDTO(165.0, "Cálculo realizado con éxito");

        Mockito.when(calculationService.calculateWithPercentage(Mockito.any(CalculationRequestDTO.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"num1\":100,\"num2\":50}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(165.0))
                .andExpect(jsonPath("$.message").value("Cálculo realizado con éxito"));
    }

    @Test
    @DisplayName("GET /api/history debe devolver una página con logs de llamadas")
    void getHistory_RetornaPaginaConLogs() throws Exception {
        CallLog log = new CallLog();
        log.setId(1L);
        log.setEndpoint("/api/calculate");
        log.setParameters("{\"num1\":100,\"num2\":50}");
        log.setResponse("{\"result\":165.0}");
        log.setError(null);
        log.setTimestamp(LocalDateTime.now());

        Pageable pageable = PageRequest.of(0, 10);
        PageImpl<CallLog> mockPage = new PageImpl<>(Collections.singletonList(log), pageable, 1);

        Mockito.when(calculationService.getCallHistory(anyInt(), anyInt())).thenReturn(mockPage);

        mockMvc.perform(get("/api/history")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].endpoint").value("/api/calculate"));
    }
}
