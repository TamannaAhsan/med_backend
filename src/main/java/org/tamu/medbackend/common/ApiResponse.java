package org.tamu.medbackend.common;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private int statusCode;

    private String status;

    private String message;

    private T data;

    private LocalDateTime timestamp;

    private Long total;

    private String traceId;

    private Object errors;

}
