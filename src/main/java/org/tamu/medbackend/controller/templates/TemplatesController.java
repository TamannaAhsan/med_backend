package org.tamu.medbackend.controller.templates;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tamu.medbackend.common.ApiResponse;
import org.tamu.medbackend.common.BaseController;
import org.tamu.medbackend.entity.doctor.DoctorProfile;
import org.tamu.medbackend.entity.patients.PatientProfile;
import org.tamu.medbackend.entity.templates.Templates;
import org.tamu.medbackend.service.auth.AuthService;
import org.tamu.medbackend.service.dtos.CreatePatientRequest;
import org.tamu.medbackend.service.dtos.CreateTemplateRequest;
import org.tamu.medbackend.service.dtos.LoginRequest;
import org.tamu.medbackend.service.dtos.PatientRegisterRequest;
import org.tamu.medbackend.service.dtos.PatientResponse;
import org.tamu.medbackend.service.dtos.RegisterRequest;
import org.tamu.medbackend.service.dtos.UpdateDoctorProfileRequest;
import org.tamu.medbackend.service.dtos.UpdatePatientProfileRequest;
import org.tamu.medbackend.service.dtos.UpdateTemplateRequest;
import org.tamu.medbackend.service.templates.TemplateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/templates")
public class TemplatesController extends BaseController {

    private final TemplateService templateService;

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<Templates>> createTemplate(
            @RequestBody CreateTemplateRequest request) {

        Templates created = templateService.createTemplate(request);

        return success(created, "Template created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<List<Templates>>> getDoctorTemplates(
            @PathVariable Long doctorId) {

        List<Templates> templates = templateService.getDoctorTemplates(doctorId);

        return success(templates, "Templates fetched successfully", HttpStatus.OK);
    }

    @GetMapping("/doctor/{doctorId}/type")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<List<Templates>>> getDoctorTemplatesByType(
            @PathVariable Long doctorId,
            @RequestParam Boolean isTest) {

        List<Templates> templates =
                templateService.getDoctorTemplatesByType(doctorId, isTest);

        return success(templates, "Templates fetched successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<Templates>> getTemplate(
            @PathVariable Long id) {

        Templates template = templateService.getTemplate(id);

        return success(template, "Template fetched successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<Templates>> updateTemplate(
            @PathVariable Long id,
            @RequestBody UpdateTemplateRequest request) {

        Templates updated = templateService.updateTemplate(id, request);

        return success(updated, "Template updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<Void>> deleteTemplate(
            @PathVariable Long id) {

        templateService.deleteTemplate(id);

        return success(null, "Template deleted successfully", HttpStatus.OK);
    }
}








