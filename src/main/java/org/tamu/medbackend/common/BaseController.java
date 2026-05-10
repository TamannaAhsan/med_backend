package org.tamu.medbackend.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public abstract class BaseController {

    // =========================
    // SUCCESS RESPONSE
    // =========================
    protected <T> ResponseEntity<ApiResponse<T>> success(
            T data,
            String message,
            HttpStatus status
    ) {
        return ResponseEntity.status(status).body(
                ApiResponse.<T>builder()
                        .statusCode(status.value())
                        .status("SUCCESS")
                        .message(message)
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // =========================
    // ERROR RESPONSE
    // =========================
    protected <T> ResponseEntity<ApiResponse<T>> error(
            String message,
            HttpStatus status
    ) {
        return ResponseEntity.status(status).body(
                ApiResponse.<T>builder()
                        .statusCode(status.value())
                        .status("ERROR")
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // =========================
    // PAGINATION RESPONSE
    // =========================
    protected <T> ResponseEntity<ApiResponse<T>> successWithTotal(
            T data,
            String message,
            Long total,
            HttpStatus status
    ) {
        return ResponseEntity.status(status).body(
                ApiResponse.<T>builder()
                        .statusCode(status.value())
                        .status("SUCCESS")
                        .message(message)
                        .data(data)
                        .total(total)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
