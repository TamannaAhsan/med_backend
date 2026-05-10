package org.tamu.medbackend.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {

    // 400
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST),
    BAD_REQUEST(HttpStatus.BAD_REQUEST),

    // 401 / 403
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
    FORBIDDEN(HttpStatus.FORBIDDEN),

    // 404
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND),
    PATIENT_NOT_FOUND(HttpStatus.NOT_FOUND),

    // 409
    CONFLICT(HttpStatus.CONFLICT),
    APPOINTMENT_CONFLICT(HttpStatus.CONFLICT),

    // 500
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

    private final HttpStatus status;

    ErrorCode(HttpStatus status) {
        this.status = status;
    }

}
