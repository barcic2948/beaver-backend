package beaverbackend.jpa.repository;

import beaverbackend.jpa.model.LabStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabStaffRepository extends JpaRepository<LabStaff, Long> {

}
