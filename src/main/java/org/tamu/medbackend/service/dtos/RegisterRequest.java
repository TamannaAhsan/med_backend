package org.tamu.medbackend.service.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String password;

    private String username;
    private String contactNumber;
    private String address;

    private Set<String> roles;

    // DOCTOR ONLY (only chambers now)
    private List<ChamberRequest> chambers;
}
