package org.tamu.medbackend.service.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdatePatientProfileRequest {
    private String username;
    private String gender;
    private String address;
    private Long chamberId;
    private LocalDate dob;
    private double height;
}
