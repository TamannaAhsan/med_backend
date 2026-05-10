package org.tamu.medbackend.exceptions;

import org.tamu.medbackend.enums.ErrorCode;

public class AppointmentConflictException extends AppException{

    public AppointmentConflictException(String message) {
        super(
                ErrorCode.APPOINTMENT_CONFLICT,
                message
        );
    }
}
