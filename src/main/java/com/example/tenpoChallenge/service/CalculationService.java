package com.example.tenpoChallenge.service;

import com.example.tenpoChallenge.dto.CalculationRequestDTO;
import com.example.tenpoChallenge.dto.CalculationResponseDTO;
import com.example.tenpoChallenge.model.CallLog;
import org.springframework.data.domain.Page;

public interface CalculationService {
    CalculationResponseDTO calculateWithPercentage(CalculationRequestDTO request);
    Page<CallLog> getCallHistory(int page, int size);
}
