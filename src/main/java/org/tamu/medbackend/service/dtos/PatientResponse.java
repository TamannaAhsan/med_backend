package org.tamu.medbackend.service.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientResponse {

    private Long id;
    private String username;
    private String gender;
    private String address;

    private Long chamberId;
    private String chamberName;

    private String createdByRole;

    private LocalDate dob;

    private double height;
}
