package org.tamu.medbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tamu.medbackend.entity.users.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
    Optional<User> findByEmail(String email);

    Optional<User> findByContactNumber(String contactNumber);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :subject OR u.contactNumber = :subject")
    Optional<User> findByEmailOrContactNumberWithRoles(@Param("subject") String subject);
}
