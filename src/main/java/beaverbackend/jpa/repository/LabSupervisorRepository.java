package beaverbackend.jpa.repository;

import beaverbackend.jpa.model.LabSupervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabSupervisorRepository extends JpaRepository<LabSupervisor, Long> {

}
