package org.tamu.medbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tamu.medbackend.entity.templates.Templates;

import java.util.List;

@Repository
public interface TemplatesRepository extends JpaRepository<Templates, Long> {

    List<Templates> findByDoctorProfileId(Long doctorId);

    List<Templates> findByDoctorProfileIdAndTestTemplate(Long doctorId, Boolean isTest);


}
