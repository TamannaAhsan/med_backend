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

@Entity
@Table(name = "doctor_chambers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DoctorChamber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chamberName;

    private String address;

    private String contactNumber;

    private String visitingHours;

    @ManyToOne
    private DoctorProfile doctorProfile;


}
