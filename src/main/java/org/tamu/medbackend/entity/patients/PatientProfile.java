package org.tamu.medbackend.entity.patients;

import jakarta.persistence.Column;
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
import org.tamu.medbackend.entity.doctor.DoctorChamber;
import org.tamu.medbackend.entity.users.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PatientProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String gender;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDate dob;

    private double height;

    @ManyToOne
    private User user;

    private String createdByRole;

    @ManyToOne
    private DoctorChamber doctorChamber;


}
