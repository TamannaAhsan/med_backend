package org.tamu.medbackend.entity.templates;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tamu.medbackend.entity.doctor.DoctorProfile;
import org.tamu.medbackend.entity.patients.PatientProfile;

import java.time.LocalDateTime;

@Entity
@Table(name = "doctor_referrals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DoctorReferral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PatientProfile patient;

    @ManyToOne
    private DoctorProfile fromDoctor;

    private String toDoctorName;

    private String toDoctorSpeciality;

    private String reason;

    private String notes;

    private String status; // optional: PENDING / SENT / DONE

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
