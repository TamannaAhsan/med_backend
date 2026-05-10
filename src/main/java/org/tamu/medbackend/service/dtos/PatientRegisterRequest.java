package org.tamu.medbackend.service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRegisterRequest {

    private String contactNumber;

    private String password;
}
