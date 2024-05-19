package beaverbackend.jpa.repository;

import beaverbackend.jpa.model.ClinicStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicStaffRepository extends JpaRepository<ClinicStaff, Long> {

}
