package org.tamu.medbackend.service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDoctorProfileRequest {

    private String specialization;
    private String degrees;
    private String achievements;
    private String experienceYears;
    private String username;
}
