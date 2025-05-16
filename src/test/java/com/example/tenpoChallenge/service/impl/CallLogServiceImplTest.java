package com.example.tenpoChallenge.service.impl;

import com.example.tenpoChallenge.model.CallLog;
import com.example.tenpoChallenge.repository.CallLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CallLogServiceImplTest {

    @Mock
    private CallLogRepository callLogRepository;

    @InjectMocks
    private CallLogServiceImpl callLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debe guardar correctamente un log de llamada")
    void testLogCall() {
        String endpoint = "/api/calculate";
        String params = "{\"num1\":100,\"num2\":50}";
        String response = "{\"result\":165.0}";
        String error = null;

        when(callLogRepository.save(any(CallLog.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        callLogService.logCall(endpoint, params, response, error);

        ArgumentCaptor<CallLog> captor = ArgumentCaptor.forClass(CallLog.class);
        verify(callLogRepository).save(captor.capture());

        CallLog savedLog = captor.getValue();
        assertEquals(endpoint, savedLog.getEndpoint());
        assertEquals(params, savedLog.getParameters());
        assertEquals(response, savedLog.getResponse());
        assertEquals(error, savedLog.getError());
    }

    @Test
    @DisplayName("Debe devolver una página de logs")
    void testGetLogs() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<CallLog> expectedPage = new PageImpl<>(Collections.emptyList());

        when(callLogRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<CallLog> result = callLogService.getLogs(pageable);

        assertEquals(expectedPage, result);
        verify(callLogRepository).findAll(pageable);
    }
}
