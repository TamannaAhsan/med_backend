package org.tamu.medbackend.exceptions;

import org.tamu.medbackend.enums.ErrorCode;

public class PatientNotFoundException extends AppException{

    public PatientNotFoundException(Long patientId) {
        super(
                ErrorCode.PATIENT_NOT_FOUND,
                "Patient not found with id: " + patientId
        );
    }
}
