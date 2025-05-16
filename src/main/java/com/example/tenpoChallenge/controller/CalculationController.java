package com.example.tenpoChallenge.controller;

import com.example.tenpoChallenge.dto.CalculationRequestDTO;
import com.example.tenpoChallenge.dto.CalculationResponseDTO;
import com.example.tenpoChallenge.model.CallLog;
import com.example.tenpoChallenge.service.CalculationService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;

    @Operation(summary = "Realiza un cálculo con un porcentaje dinámico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cálculo exitoso"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponseDTO> calculate(@Valid @RequestBody CalculationRequestDTO request) {
        return ResponseEntity.ok(calculationService.calculateWithPercentage(request));
    }

    @Operation(summary = "Obtiene el historial de llamadas con paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/history")
    public Page<CallLog> getHistory(
            @RequestParam(value = "page")
            @Parameter(description = "Número de página (comienza desde 0)", example = "0")
            int page,
            @RequestParam(value = "size")
            @Parameter(description = "Cantidad de registros por página", example = "10")
            int size
    ) {
        return calculationService.getCallHistory(page, size);
    }
}