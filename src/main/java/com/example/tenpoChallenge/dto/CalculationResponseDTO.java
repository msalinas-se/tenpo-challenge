package com.example.tenpoChallenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa la respuesta del cálculo con el porcentaje aplicado.")
public class CalculationResponseDTO {

    @Schema(description = "Resultado final del cálculo con porcentaje incluido", example = "18.15")
    private Double result;

    @Schema(description = "Mensaje adicional informativo", example = "Cálculo realizado con éxito")
    private String message;
}