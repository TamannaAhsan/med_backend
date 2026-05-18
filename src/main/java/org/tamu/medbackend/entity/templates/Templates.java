package org.tamu.medbackend.entity.templates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import org.tamu.medbackend.entity.doctor.DoctorProfile;

import java.time.LocalDateTime;

@Entity
@Table(name = "templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Templates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Boolean testTemplate;

    private Boolean active = true;

    @ManyToOne
    @JsonIgnoreProperties({"user"})
    private DoctorProfile doctorProfile;

    @ManyToOne
    @JsonIgnoreProperties({"doctorProfile"})
    private DoctorChamber doctorChamber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
