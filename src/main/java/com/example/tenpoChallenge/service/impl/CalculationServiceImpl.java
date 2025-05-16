package com.example.tenpoChallenge.service.impl;

import com.example.tenpoChallenge.dto.CalculationRequestDTO;
import com.example.tenpoChallenge.dto.CalculationResponseDTO;
import com.example.tenpoChallenge.exception.CalculationException;
import com.example.tenpoChallenge.model.CallLog;
import com.example.tenpoChallenge.service.CalculationService;
import com.example.tenpoChallenge.service.CallLogService;
import com.example.tenpoChallenge.service.PercentageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculationServiceImpl implements CalculationService {

    private final PercentageService percentageService;
    private final CallLogService callLogService;

    @Override
    public CalculationResponseDTO calculateWithPercentage(CalculationRequestDTO request) {
        String endpoint = "/api/calculate";
        String parameters = request.toString();
        try {
            log.info("Received calculation request with parameters: {}", parameters);
            double num1 = request.getNum1();
            double num2 = request.getNum2();
            double sum = num1 + num2;
            double percentage = percentageService.getPercentage();
            double result = sum * (1 + percentage);
            log.info("Calculation result: {}", result);

            callLogService.logCall(endpoint, parameters, String.valueOf(result), null);
            return new CalculationResponseDTO(result, "Cálculo realizado con éxito");
        } catch (Exception e) {
            String errorMessage = "Error durante el cálculo: " + e.getMessage();
            log.error(errorMessage, e);
            callLogService.logCall(endpoint, parameters, null, errorMessage);
            throw new CalculationException(errorMessage, e);
        }
    }

    @Override
    public Page<CallLog> getCallHistory(int page, int size) {
        return callLogService.getLogs(PageRequest.of(page, size));
    }
}
