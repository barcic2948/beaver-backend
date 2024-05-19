package beaverbackend.service;

import beaverbackend.controllers.receptionist.PatientSearchReq;
import beaverbackend.jpa.model.Patient;
import beaverbackend.jpa.repository.PatientRepository;
import beaverbackend.jpa.specification.PatientSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public List<Patient> searchPatients(PatientSearchReq req) {
        return patientRepository.findAll(PatientSpecification.searchSpecification(req));
    }

    @Override
    public Patient findByPatientInsuranceId(String insuranceId) {
        return patientRepository.findByInsuranceId(insuranceId).orElse(null);
    }
}
