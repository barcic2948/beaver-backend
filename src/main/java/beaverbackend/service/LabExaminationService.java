package beaverbackend.service;

import beaverbackend.controllers.assistant.CancelLabExaminationReq;
import beaverbackend.controllers.assistant.CompleteLabExaminationReq;
import beaverbackend.controllers.common.LabExaminationSearchReq;
import beaverbackend.controllers.supervisor.ApproveLabExaminationReq;
import beaverbackend.controllers.supervisor.RejectLabExaminationReq;
import beaverbackend.jpa.model.LabExamination;

import java.util.List;

public interface LabExaminationService {

    public List<LabExamination> assistantSearchLabExamination(LabExaminationSearchReq req);

    public List<LabExamination> supervisorSearchLabExamination(LabExaminationSearchReq req);

    public LabExamination cancelLabExamination(CancelLabExaminationReq req);

    public LabExamination completeLabExamination(CompleteLabExaminationReq req);

    public LabExamination approveLabExamination(ApproveLabExaminationReq req);

    public LabExamination rejectLabExamination(RejectLabExaminationReq req);

}
