package org.tamu.medbackend.service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReferralRequest {

    private Long patientId;

    private Long doctorId;

    private String toDoctorName;

    private String toDoctorSpeciality;

    private String reason;

    private String notes;
}
