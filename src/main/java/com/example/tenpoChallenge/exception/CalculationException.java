package com.example.tenpoChallenge.exception;

public class CalculationException extends RuntimeException {
    public CalculationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalculationException(String message) {
        super(message);
    }
}
