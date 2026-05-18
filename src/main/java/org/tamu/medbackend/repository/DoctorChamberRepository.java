package org.tamu.medbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tamu.medbackend.entity.doctor.DoctorChamber;

import java.util.List;

@Repository
public interface DoctorChamberRepository extends JpaRepository<DoctorChamber, Long> {

    List<DoctorChamber> findByDoctorProfileId(Long doctorProfileId);
}
