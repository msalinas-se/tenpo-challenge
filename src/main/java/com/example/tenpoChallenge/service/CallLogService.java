package com.example.tenpoChallenge.service;

import com.example.tenpoChallenge.model.CallLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CallLogService {
    void logCall(String endpoint, String params, String response, String error);
    Page<CallLog> getLogs(Pageable pageable);
}
