package org.tamu.medbackend.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.tamu.medbackend.common.ApiResponse;
import org.tamu.medbackend.common.BaseController;
import org.tamu.medbackend.entity.doctor.DoctorProfile;
import org.springframework.web.bind.annotation.*;
import org.tamu.medbackend.entity.patients.PatientProfile;
import org.tamu.medbackend.service.auth.AuthService;
import org.tamu.medbackend.service.dtos.CreatePatientRequest;
import org.tamu.medbackend.service.dtos.LoginRequest;
import org.tamu.medbackend.service.dtos.PatientRegisterRequest;
import org.tamu.medbackend.service.dtos.PatientResponse;
import org.tamu.medbackend.service.dtos.RegisterRequest;
import org.tamu.medbackend.service.dtos.UpdateDoctorProfileRequest;
import org.tamu.medbackend.service.dtos.UpdatePatientProfileRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController extends BaseController {

    private final AuthService authService;

    @PostMapping("/doctor-register")
    public ResponseEntity<ApiResponse<Void>> registerDoctor(@RequestBody RegisterRequest request) {

        authService.registerDoctor(request);

        return success(null, "User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/patient-register")
    public ResponseEntity<ApiResponse<Void>> registerPatient(@RequestBody PatientRegisterRequest request) {

        authService.registerPatient(request);

        return success(null, "User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/doctor-login")
    public ResponseEntity<ApiResponse<String>> loginDoctor(@RequestBody LoginRequest request) {

        String token = authService.loginDoctor(request.getEmail(), request.getPassword());

        return success(token, "Login successful", HttpStatus.OK);
    }

    @PostMapping("/patient-login")
    public ResponseEntity<ApiResponse<String>> loginPatient(
            @RequestBody LoginRequest request) {

        String token = authService.patientLogin(
                request.getContactNumber(),
                request.getPassword()
        );

        return success(token, "Login successful", HttpStatus.OK);
    }

    @PostMapping("/create-patient")
    @PreAuthorize("hasAnyRole('DOCTOR', 'REPRESENTATIVE')")
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(
            @RequestBody CreatePatientRequest request) {

        PatientResponse created = authService.createPatient(request);

        return success(created, "Patient created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/update-doctor")
    public ResponseEntity<ApiResponse<DoctorProfile>> updateDoctorProfile(
            @RequestParam Long userId,
            @RequestBody UpdateDoctorProfileRequest request) {

        DoctorProfile updated = authService.updateDoctorProfile(userId, request);

        return success(updated, "Doctor profile updated successfully", HttpStatus.OK);
    }

    @PutMapping("/patient/profile/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'REPRESENTATIVE')")
    public ResponseEntity<ApiResponse<PatientProfile>> updatePatient(
            @PathVariable Long id,
            @RequestBody UpdatePatientProfileRequest request) {

        PatientProfile updated = authService.updatePatientProfile(id, request);

        return success(updated, "Patient profile updated successfully", HttpStatus.OK);
    }





}
