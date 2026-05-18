package org.tamu.medbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tamu.medbackend.entity.templates.DoctorReferral;

import java.util.List;

@Repository
public interface DoctorReferralRepository extends JpaRepository<DoctorReferral, Long> {
    List<DoctorReferral> findByFromDoctorId(Long doctorId);

    List<DoctorReferral> findByFromDoctorIdAndStatus(Long doctorId, String status);

    List<DoctorReferral> findByPatientId(Long patientId);

}
