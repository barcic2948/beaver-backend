package beaverbackend.jpa.repository;

import beaverbackend.jpa.model.PhysicalExamination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalExaminationRepository extends JpaRepository<PhysicalExamination, Long> {

}
