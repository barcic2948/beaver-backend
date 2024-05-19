package beaverbackend.jpa.repository;

import beaverbackend.jpa.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {

    public boolean existsByNpwzId(String npwzId);

    public Optional<Doctor> findByNpwzId(String npwzId);
}
