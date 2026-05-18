package org.tamu.medbackend.service.templates;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.tamu.medbackend.entity.doctor.DoctorChamber;
import org.tamu.medbackend.entity.doctor.DoctorProfile;
import org.tamu.medbackend.entity.patients.PatientProfile;
import org.tamu.medbackend.entity.templates.DoctorReferral;
import org.tamu.medbackend.entity.templates.Templates;
import org.tamu.medbackend.repository.DoctorChamberRepository;
import org.tamu.medbackend.repository.DoctorProfileRepository;
import org.tamu.medbackend.repository.DoctorReferralRepository;
import org.tamu.medbackend.repository.PatientProfileRepository;
import org.tamu.medbackend.repository.TemplatesRepository;
import org.tamu.medbackend.service.dtos.CreateReferralRequest;
import org.tamu.medbackend.service.dtos.CreateTemplateRequest;
import org.tamu.medbackend.service.dtos.UpdateReferralRequest;
import org.tamu.medbackend.service.dtos.UpdateTemplateRequest;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TemplateService {

    private final TemplatesRepository templatesRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final DoctorChamberRepository doctorChamberRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final DoctorReferralRepository referralRepository;

    @Transactional
    public Templates createTemplate(CreateTemplateRequest request) {

        DoctorProfile doctor = doctorProfileRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        DoctorChamber chamber = doctorChamberRepository.findById(request.getChamberId())
                .orElseThrow(() -> new RuntimeException("Chamber not found"));

        Templates template = new Templates();

        template.setTitle(request.getTitle());
        template.setContent(request.getContent());
        template.setDescription(request.getDescription());
        template.setTestTemplate(request.getTestTemplate());

        template.setDoctorProfile(doctor);
        template.setDoctorChamber(chamber);

        template.setActive(true);

        template.setCreatedAt(LocalDateTime.now());
        template.setUpdatedAt(LocalDateTime.now());

        return templatesRepository.save(template);
    }

    public List<Templates> getDoctorTemplates(Long doctorId) {

        return templatesRepository.findByDoctorProfileId(doctorId);
    }

    public List<Templates> getDoctorTemplatesByType(Long doctorId, Boolean isTest) {

        return templatesRepository.findByDoctorProfileIdAndTestTemplate(doctorId, isTest);
    }

    public Templates getTemplate(Long id) {

        return templatesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));
    }

    @Transactional
    public Templates updateTemplate(Long id, UpdateTemplateRequest request) {

        Templates template = templatesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        template.setTitle(request.getTitle());
        template.setContent(request.getContent());
        template.setDescription(request.getDescription());
        template.setTestTemplate(request.getTestTemplate());
        template.setActive(request.getActive());

        template.setUpdatedAt(LocalDateTime.now());

        return templatesRepository.save(template);
    }

    @Transactional
    public void deleteTemplate(Long id) {

        Templates template = templatesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        template.setActive(false);

        templatesRepository.save(template);
    }

    @Transactional
    public DoctorReferral createReferral(CreateReferralRequest request) {

        DoctorProfile fromDoctor = doctorProfileRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        PatientProfile patient = patientProfileRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        DoctorReferral referral = new DoctorReferral();

        referral.setFromDoctor(fromDoctor);
        referral.setPatient(patient);

        referral.setToDoctorName(request.getToDoctorName());
        referral.setToDoctorSpeciality(request.getToDoctorSpeciality());

        referral.setReason(request.getReason());
        referral.setNotes(request.getNotes());

        referral.setStatus("SENT");

        referral.setCreatedAt(LocalDateTime.now());
        referral.setUpdatedAt(LocalDateTime.now());

        return referralRepository.save(referral);
    }

    // READ ALL (by doctor)
    public List<DoctorReferral> getByDoctor(Long doctorId) {
        return referralRepository.findByFromDoctorId(doctorId);
    }

    // READ SINGLE
    public DoctorReferral getById(Long id) {
        return referralRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Referral not found"));
    }

    // UPDATE
    @Transactional
    public DoctorReferral update(Long id, UpdateReferralRequest request) {

        DoctorReferral referral = getById(id);

        referral.setToDoctorName(request.getToDoctorName());
        referral.setToDoctorSpeciality(request.getToDoctorSpeciality());
        referral.setReason(request.getReason());
        referral.setNotes(request.getNotes());
        referral.setStatus(request.getStatus());
        referral.setUpdatedAt(LocalDateTime.now());

        return referralRepository.save(referral);
    }

    // DELETE
    @Transactional
    public void delete(Long id) {

        DoctorReferral referral = getById(id);

        referralRepository.delete(referral);
    }
}














