package org.tamu.medbackend.service.templates;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.tamu.medbackend.entity.doctor.DoctorChamber;
import org.tamu.medbackend.entity.doctor.DoctorProfile;
import org.tamu.medbackend.entity.templates.Templates;
import org.tamu.medbackend.repository.DoctorChamberRepository;
import org.tamu.medbackend.repository.DoctorProfileRepository;
import org.tamu.medbackend.repository.TemplatesRepository;
import org.tamu.medbackend.service.dtos.CreateTemplateRequest;
import org.tamu.medbackend.service.dtos.UpdateTemplateRequest;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TemplateService {

    private final TemplatesRepository templatesRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final DoctorChamberRepository doctorChamberRepository;

    @Transactional
    public Templates createTemplate(CreateTemplateRequest request) {

        DoctorProfile doctor = doctorProfileRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        DoctorChamber chamber = doctorChamberRepository.findById(request.getChamberId())
                .orElseThrow(() -> new RuntimeException("Chamber not found"));

        Templates template = new Templates();

        template.setTitle(request.getTitle());
        template.setContent(request.getContent());
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

        return templatesRepository.findByDoctorProfileIdAndIsTest(doctorId, isTest);
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
}













