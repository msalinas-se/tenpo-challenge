package com.example.tenpoChallenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "DTO que representa una solicitud de cálculo con dos números.")
public class CalculationRequestDTO {

    @NotNull(message = "El número 1 no puede ser nulo")
    @Schema(description = "Primer número para el cálculo", example = "10.5", required = true)
    private Double num1;

    @NotNull(message = "El número 2 no puede ser nulo")
    @Schema(description = "Segundo número para el cálculo", example = "5.5", required = true)
    private Double num2;
}