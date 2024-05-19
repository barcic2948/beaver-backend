package beaverbackend.service;

import beaverbackend.controllers.common.BadRequestException;
import beaverbackend.controllers.doctor.CreateLabExaminationReq;
import beaverbackend.controllers.doctor.CreatePhysicalExaminationReq;
import beaverbackend.controllers.doctor.VisitExaminationListSearchReq;
import beaverbackend.controllers.doctor.VisitExaminationSearchRes;
import beaverbackend.controllers.receptionist.DoctorSearchReq;
import beaverbackend.jpa.model.Doctor;
import beaverbackend.jpa.model.LabExamination;
import beaverbackend.jpa.model.PhysicalExamination;

import java.util.List;

public interface DoctorService {
    List<Doctor> searchDoctors(DoctorSearchReq req);

    Doctor findByNpwzId(String npwzId);

    PhysicalExamination createPhysicalExamination(CreatePhysicalExaminationReq req) throws BadRequestException;

    List<PhysicalExamination> getVistPhysicalExaminationList(Long visitId) throws BadRequestException;

    LabExamination createLabExamination(CreateLabExaminationReq req) throws BadRequestException;

    List<LabExamination> getVistLabExaminationList(Long visitId) throws BadRequestException;

    VisitExaminationSearchRes getVisitExaminationList(VisitExaminationListSearchReq req) throws BadRequestException;
}
