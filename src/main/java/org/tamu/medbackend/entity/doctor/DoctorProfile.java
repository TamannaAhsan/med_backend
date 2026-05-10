package org.tamu.medbackend.entity.doctor;

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
import org.tamu.medbackend.entity.users.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "doctor_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DoctorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specialization;

    private String degrees; // MBBS, FCPS, etc (can later normalize)

    private String achievements;

    private String experienceYears;

    private String username;

    @ManyToOne
    private User user;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
