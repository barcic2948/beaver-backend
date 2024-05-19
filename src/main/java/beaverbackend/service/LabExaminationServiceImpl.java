package beaverbackend.service;

import beaverbackend.controllers.assistant.CancelLabExaminationReq;
import beaverbackend.controllers.assistant.CompleteLabExaminationReq;
import beaverbackend.controllers.common.BadRequestException;
import beaverbackend.controllers.common.LabExaminationSearchReq;
import beaverbackend.controllers.supervisor.ApproveLabExaminationReq;
import beaverbackend.controllers.supervisor.RejectLabExaminationReq;
import beaverbackend.enums.BadRequestDictEnum;
import beaverbackend.enums.LaboratoryStatusEnum;
import beaverbackend.jpa.model.AppUser;
import beaverbackend.jpa.model.LabAssistant;
import beaverbackend.jpa.model.LabExamination;
import beaverbackend.jpa.model.LabSupervisor;
import beaverbackend.jpa.repository.AppUserRepository;
import beaverbackend.jpa.repository.LabExaminationRepository;
import beaverbackend.jpa.specification.LabExaminationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LabExaminationServiceImpl implements LabExaminationService {

    private final LabExaminationRepository labExaminationRepository;
    private final AppUserRepository appUserRepository;


    @Override
    public List<LabExamination> assistantSearchLabExamination(LabExaminationSearchReq req) {

        LaboratoryStatusEnum status;

        try {
            status = LaboratoryStatusEnum.valueOf(req.getStatus());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_STATUS, e.getMessage());
        } catch (NullPointerException e) {
            status = null;
        }

        return labExaminationRepository.findAll(LabExaminationSpecification.searchSpecification(status, null, null, req.getExaminationCode()));
    }

    @Override
    public List<LabExamination> supervisorSearchLabExamination(LabExaminationSearchReq req) {
        LaboratoryStatusEnum status;

        try {
            status = LaboratoryStatusEnum.valueOf(req.getStatus());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_STATUS, e.getMessage());
        } catch (NullPointerException e) {
            status = null;
        }

        Long assistantId;
        try {
            assistantId = Long.valueOf(req.getLabAssistantId());
        } catch (NumberFormatException e) {
            throw new BadRequestException((BadRequestDictEnum.BAD_VALUE), e.getMessage());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_ASSISTANT_ID, authentication.getName()));

        LabSupervisor labSupervisor = appUser.getPerson().getLabStaff().getSupervisor();

        return labExaminationRepository.findAll(LabExaminationSpecification.searchSpecification(status, null, null, req.getExaminationCode()));
    }


    @Override
    public LabExamination cancelLabExamination(CancelLabExaminationReq req) {

        if (req.getExaminationId() == null)
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, null);

        LabExamination examination = labExaminationRepository.findById(req.getExaminationId())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, req.getExaminationId().toString()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_ASSISTANT_ID, authentication.getName()));
        LabAssistant labAssistant = appUser.getPerson().getLabStaff().getAssistant();

        examination.setLabAssistant(labAssistant);
        examination.setCancellationReason(req.getCancellationReason());
        examination.setExecutionDateTime(LocalDateTime.now());
        examination.setStatus(LaboratoryStatusEnum.CANCELLED);

        return labExaminationRepository.save(examination);
    }

    @Override
    public LabExamination completeLabExamination(CompleteLabExaminationReq req) {
        if (req.getExaminationId() == null)
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, null);

        if (req.getResult() == null || req.getResult().isEmpty()) {
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_RESULT, null);
        }

        LabExamination examination = labExaminationRepository.findById(req.getExaminationId())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, req.getExaminationId().toString()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_ASSISTANT_ID, authentication.getName()));
        LabAssistant labAssistant = appUser.getPerson().getLabStaff().getAssistant();

        examination.setLabAssistant(labAssistant);
        examination.setResult(req.getResult());
        examination.setExecutionDateTime(LocalDateTime.now());
        examination.setStatus(LaboratoryStatusEnum.COMPLETED);

        return labExaminationRepository.save(examination);
    }

    @Override
    public LabExamination approveLabExamination(ApproveLabExaminationReq req) {
        if (req.getExaminationId() == null)
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, null);

        LabExamination examination = labExaminationRepository.findById(req.getExaminationId())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, req.getExaminationId().toString()));

        if (examination.getLabAssistant() != null)
            throw new BadRequestException(BadRequestDictEnum.MISSING_ASSISTANT, null);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_ASSISTANT_ID, authentication.getName()));

        LabSupervisor labSupervisor = appUser.getPerson().getLabStaff().getSupervisor();

        examination.setLabSupervisor(labSupervisor);
        examination.setSupervisorNotices(req.getSupervisorNotices());
        examination.setApprovalDateTime(LocalDateTime.now());
        examination.setStatus(LaboratoryStatusEnum.APPROVED);

        return labExaminationRepository.save(examination);
    }

    @Override
    public LabExamination rejectLabExamination(RejectLabExaminationReq req) {
        if (req.getExaminationId() == null)
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, null);

        LabExamination examination = labExaminationRepository.findById(req.getExaminationId())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, req.getExaminationId().toString()));

        if (examination.getLabAssistant() != null)
            throw new BadRequestException(BadRequestDictEnum.MISSING_ASSISTANT, null);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_ASSISTANT_ID, authentication.getName()));

        LabSupervisor labSupervisor = appUser.getPerson().getLabStaff().getSupervisor();

        examination.setLabSupervisor(labSupervisor);
        examination.setSupervisorNotices(req.getSupervisorNotices());
        examination.setApprovalDateTime(LocalDateTime.now());
        examination.setStatus(LaboratoryStatusEnum.REJECTED);

        return labExaminationRepository.save(examination);
    }
}
