package beaverbackend.jpa.repository;

import beaverbackend.jpa.model.LabExamination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LabExaminationRepository extends JpaRepository<LabExamination, Long>, JpaSpecificationExecutor<LabExamination> {

}
