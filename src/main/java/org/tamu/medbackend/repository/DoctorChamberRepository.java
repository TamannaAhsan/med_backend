package org.tamu.medbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tamu.medbackend.entity.doctor.DoctorChamber;
import org.tamu.medbackend.entity.users.Role;

import java.util.Optional;

@Repository
public interface DoctorChamberRepository extends JpaRepository<DoctorChamber, Long> {


}
