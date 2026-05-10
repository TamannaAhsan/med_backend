package org.tamu.medbackend.service.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreatePatientRequest {
    private String username;
    private String gender;
    private String address;
    private String contactNumber;
    private String email;
    private String password;
    private Long chamberId;
    private String  chamberName;
    private LocalDate dob;
    private double height;
}
