package com.example.tenpoChallenge.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CALL_LOGS", schema = "LOGGING")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    private String endpoint;

    @Column(length = 1000)
    private String parameters;

    @Column(length = 1000)
    private String response;

    @Column(length = 1000)
    private String error;
}
