package beaverbackend.jpa.repository;

import beaverbackend.jpa.model.ExaminationDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExaminationDictionaryRepository extends JpaRepository<ExaminationDictionary, Long>, JpaSpecificationExecutor<ExaminationDictionary> {

    Optional<ExaminationDictionary> findByCode(String code);

}
