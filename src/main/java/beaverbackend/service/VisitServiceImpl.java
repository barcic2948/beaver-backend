package beaverbackend.service;

import beaverbackend.controllers.common.BadRequestException;
import beaverbackend.controllers.common.VisitSearchReq;
import beaverbackend.controllers.doctor.SetVisitStatusReq;
import beaverbackend.controllers.receptionist.VisitCreateReq;
import beaverbackend.enums.BadRequestDictEnum;
import beaverbackend.enums.VisitStatusEnum;
import beaverbackend.jpa.model.*;
import beaverbackend.jpa.repository.AppUserRepository;
import beaverbackend.jpa.repository.DoctorRepository;
import beaverbackend.jpa.repository.PatientRepository;
import beaverbackend.jpa.repository.VisitRepository;
import beaverbackend.jpa.specification.VisitSpecification;
import beaverbackend.utils.BeaverUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppUserRepository appUserRepository;


    @Override
    public Visit createNewVisit(VisitCreateReq req) throws BadRequestException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_RECEPTIONIST_ID, authentication.getName()));
        Receptionist receptionist = appUser.getPerson().getClinicStaff().getReceptionist();
        Doctor doctor = doctorRepository.findByNpwzId(req.getDoctorNpwzId())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_DOCTOR_NPWZ_ID, req.getDoctorNpwzId()));
        Patient patient = patientRepository.findByInsuranceId(req.getPatientInsuranceId())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_PATIENT_INSURANCE_ID, req.getPatientInsuranceId()));
        LocalDateTime visitDateTime;
        try {
            visitDateTime = BeaverUtils.convertReqToDateTime(req.getScheduledDateTime());
        } catch (DateTimeParseException e) {
            throw new BadRequestException(BadRequestDictEnum.BAD_DATE, req.getScheduledDateTime());
        }

        Visit visit = new Visit(req.getDescription(), receptionist, doctor, patient, visitDateTime);
        return visitRepository.save(visit);
    }

    @Override
    public List<Visit> searchVisits(VisitSearchReq req) {
        return visitRepository.findAll(VisitSpecification.searchSpecification(req));
    }

    @Override
    public Visit cancelVisit(Long visitId) throws BadRequestException {
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_VISIT_ID, visitId.toString()));
        visit.setVisitStatus(VisitStatusEnum.CANCELLED);
        return visitRepository.save(visit);
    }

    @Override
    public Visit setVisitStatus(SetVisitStatusReq req) throws BadRequestException {

        if (req.getVisitId() == null) {
            throw new BadRequestException(BadRequestDictEnum.BAD_VISIT_ID, null);
        }
        Visit visit = visitRepository.findById(req.getVisitId()).orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_VISIT_ID, req.getVisitId().toString()));
        try {
            visit.setVisitStatus(VisitStatusEnum.valueOf(req.getVisitStatus()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(BadRequestDictEnum.BAD_VISIT_STATUS, req.getVisitStatus());
        } catch (NullPointerException e) {
            throw new BadRequestException(BadRequestDictEnum.BAD_VISIT_STATUS, null);
        }

        return visitRepository.save(visit);

    }
}
