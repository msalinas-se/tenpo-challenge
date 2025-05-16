package com.example.tenpoChallenge.repository;

import com.example.tenpoChallenge.model.CallLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallLogRepository extends JpaRepository<CallLog, Long> {
}
