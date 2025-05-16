package com.example.tenpoChallenge.service.impl;

import com.example.tenpoChallenge.model.CallLog;
import com.example.tenpoChallenge.repository.CallLogRepository;
import com.example.tenpoChallenge.service.CallLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CallLogServiceImpl implements CallLogService {

    private final CallLogRepository callLogRepository;

    @Async
    @Override
    public void logCall(String endpoint, String params, String response, String error) {
        CallLog log = CallLog.builder()
                .timestamp(LocalDateTime.now())
                .endpoint(endpoint)
                .parameters(params)
                .response(response)
                .error(error)
                .build();
        callLogRepository.save(log);
    }

    @Override
    public Page<CallLog> getLogs(Pageable pageable) {
        return callLogRepository.findAll(pageable);
    }
}
