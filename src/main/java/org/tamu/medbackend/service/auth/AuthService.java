package org.tamu.medbackend.service.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.tamu.medbackend.entity.doctor.DoctorChamber;
import org.tamu.medbackend.entity.doctor.DoctorProfile;
import org.tamu.medbackend.entity.patients.PatientProfile;
import org.tamu.medbackend.entity.users.Role;
import org.tamu.medbackend.entity.users.User;
import org.tamu.medbackend.repository.DoctorChamberRepository;
import org.tamu.medbackend.repository.DoctorProfileRepository;
import org.tamu.medbackend.repository.PatientProfileRepository;
import org.tamu.medbackend.repository.RoleRepository;
import org.tamu.medbackend.repository.UserRepository;
import org.tamu.medbackend.security.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tamu.medbackend.service.dtos.CreatePatientRequest;
import org.tamu.medbackend.service.dtos.PatientRegisterRequest;
import org.tamu.medbackend.service.dtos.PatientResponse;
import org.tamu.medbackend.service.dtos.RegisterRequest;
import org.tamu.medbackend.service.dtos.DoctorLoginResponse;
import org.tamu.medbackend.service.dtos.UpdateDoctorProfileRequest;
import org.tamu.medbackend.service.dtos.UpdatePatientProfileRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final DoctorChamberRepository doctorChamberRepository;
    private final PatientProfileRepository patientProfileRepository;


    @Transactional
    public void registerDoctor(RegisterRequest request) {

        // 1. check existing user
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // 2. validate chambers
        if (request.getChambers() == null || request.getChambers().isEmpty()) {
            throw new RuntimeException("Doctor must create at least one chamber");
        }

        // 3. get doctor role
        Role doctorRole = roleRepository.findByName("DOCTOR")
                .orElseThrow(() -> new RuntimeException("Doctor role not found"));

        // 4. create user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setContactNumber(request.getContactNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(doctorRole));

        User savedUser = userRepository.save(user);

        // 5. create doctor profile
        DoctorProfile doctorProfile = new DoctorProfile();
        doctorProfile.setUser(savedUser);

        DoctorProfile savedDoctor = doctorProfileRepository.save(doctorProfile);

        // 6. create chambers
        List<DoctorChamber> chambers = request.getChambers().stream()
                .map(c -> {
                    DoctorChamber chamber = new DoctorChamber();

                    chamber.setChamberName(c.getChamberName());
                    chamber.setAddress(c.getAddress());
                    chamber.setContactNumber(c.getContactNumber());
                    chamber.setVisitingHours(c.getVisitingHours());

                    chamber.setDoctorProfile(savedDoctor);

                    return chamber;
                })
                .toList();

        doctorChamberRepository.saveAll(chambers);
    }

    @Transactional
    public void registerPatient(PatientRegisterRequest request) {

        // 1. check existing patient/user
        if (userRepository.findByContactNumber(request.getContactNumber()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // 2. get patient role
        Role patientRole = roleRepository.findByName("PATIENT")
                .orElseThrow(() -> new RuntimeException("Patient role not found"));

        // 3. create auth user
        User user = new User();
        user.setContactNumber(request.getContactNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(patientRole));

        User savedUser = userRepository.save(user);

        // 4. create empty patient profile
        // patient will complete later
        PatientProfile patientProfile = new PatientProfile();

        patientProfile.setUser(savedUser);

        patientProfileRepository.save(patientProfile);
    }

    public DoctorLoginResponse loginDoctor(String email, String password, Long chamberId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DoctorProfile profile = doctorProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Doctor profile not found"));

        List<DoctorChamber> chambers = doctorChamberRepository.findByDoctorProfileId(profile.getId());

        if (chambers.isEmpty()) {
            throw new RuntimeException("Doctor has no chambers");
        }

        List<DoctorLoginResponse.ChamberSummary> chamberSummaries = chambers.stream()
                .map(this::toChamberSummary)
                .toList();

        // Phase 1: email only — return chambers without token
        if (password == null || password.isBlank()) {
            DoctorLoginResponse response = new DoctorLoginResponse();
            response.setDoctorId(profile.getId());
            response.setChambers(chamberSummaries);
            return response;
        }

        // Phase 2: full login
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (chamberId == null) {
            throw new RuntimeException("Chamber is required");
        }

        boolean chamberBelongsToDoctor = chambers.stream()
                .anyMatch(c -> c.getId().equals(chamberId));

        if (!chamberBelongsToDoctor) {
            throw new RuntimeException("Invalid chamber for this doctor");
        }

        DoctorLoginResponse response = new DoctorLoginResponse();
        response.setToken(jwtService.generateToken(user.getEmail()));
        response.setDoctorId(profile.getId());
        response.setSelectedChamberId(chamberId);
        response.setChambers(chamberSummaries);
        return response;
    }

    private DoctorLoginResponse.ChamberSummary toChamberSummary(DoctorChamber chamber) {
        DoctorLoginResponse.ChamberSummary summary = new DoctorLoginResponse.ChamberSummary();
        summary.setId(chamber.getId());
        summary.setChamberName(chamber.getChamberName());
        return summary;
    }

    public String patientLogin(String contactNumber, String password) {

        User user = userRepository.findByContactNumber(contactNumber)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        boolean isPatient = user.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase("PATIENT"));

        if (!isPatient) {
            throw new RuntimeException("User is not a patient");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(user.getContactNumber());
    }

    @Transactional
    public PatientResponse createPatient(CreatePatientRequest request) {

        User creator = getCurrentUserForMutation();

        if (request.getChamberId() == null) {
            throw new RuntimeException("Chamber is required");
        }

        // duplicate check
        userRepository.findByContactNumber(request.getContactNumber())
                .ifPresent(u -> {
                    throw new RuntimeException("Patient already exists");
                });

        Role patientRole = roleRepository.findByName("PATIENT")
                .orElseThrow(() -> new RuntimeException("PATIENT role not found"));

        DoctorChamber chamber = doctorChamberRepository.findById(request.getChamberId())
                .orElseThrow(() -> new RuntimeException("Chamber not found"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setContactNumber(request.getContactNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(patientRole));

        User savedUser = userRepository.save(user);

        PatientProfile profile = new PatientProfile();
        profile.setUsername(request.getUsername());
        profile.setGender(request.getGender());
        profile.setAddress(request.getAddress());
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());
        profile.setDoctorChamber(chamber);
        profile.setUser(savedUser);
        profile.setDob(request.getDob());
        profile.setHeight(request.getHeight());

        String creatorRole = creator.getRoles().stream()
                .map(Role::getName)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse("UNKNOWN");

        profile.setCreatedByRole(creatorRole);

        PatientProfile savedProfile = patientProfileRepository.save(profile);

        PatientResponse response = new PatientResponse();
        response.setId(savedProfile.getId());
        response.setUsername(savedProfile.getUsername());
        response.setGender(savedProfile.getGender());
        response.setAddress(savedProfile.getAddress());
        response.setChamberId(chamber.getId());
        response.setChamberName(chamber.getChamberName());
        response.setCreatedByRole(creatorRole);
        response.setDob(savedProfile.getDob());
        response.setHeight(savedProfile.getHeight());

        return response;
    }

    @Transactional
    public DoctorProfile updateDoctorProfile(Long userId, UpdateDoctorProfileRequest request) {

        // 1. find doctor profile by userId
        DoctorProfile profile = doctorProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Doctor profile not found"));

        // 2. update ONLY profile fields (NOT USER)
        profile.setSpecialization(request.getSpecialization());
        profile.setDegrees(request.getDegrees());
        profile.setAchievements(request.getAchievements());
        profile.setExperienceYears(request.getExperienceYears());
        profile.setUsername(request.getUsername());
        profile.setUpdatedAt(LocalDateTime.now());

        return doctorProfileRepository.save(profile);
    }

    @Transactional
    public PatientProfile updatePatientProfile(Long patientProfileId,
                                               UpdatePatientProfileRequest request) {

        PatientProfile profile = patientProfileRepository.findById(patientProfileId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        profile.setUsername(request.getUsername());
        profile.setGender(request.getGender());
        profile.setAddress(request.getAddress());
        profile.setUpdatedAt(LocalDateTime.now());
        profile.setDob(request.getDob());
        profile.setHeight(request.getHeight());

        if (request.getChamberId() != null) {
            DoctorChamber chamber = doctorChamberRepository.findById(request.getChamberId())
                    .orElseThrow(() -> new RuntimeException("Chamber not found"));

            profile.setDoctorChamber(chamber);
        }

        return patientProfileRepository.save(profile);
    }

    private User getCurrentUserForMutation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Not authenticated");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User detached)) {
            throw new RuntimeException("Invalid authentication");
        }
        return userRepository.findById(detached.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }




}
