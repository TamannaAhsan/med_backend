package org.tamu.medbackend.service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePatientProfileRequest {
    private String username;
    private String gender;
    private String address;
    private Long chamberId;
}
