package beaverbackend.controllers.supervisor;

import beaverbackend.controllers.common.LabExaminationSearchReq;
import beaverbackend.jpa.model.LabExamination;
import beaverbackend.service.LabExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supervisor")
@RequiredArgsConstructor
public class SupervisorController {

    private final LabExaminationService labExaminationService;

    @PreAuthorize("hasAuthority('SCOPE_LAB_SUPER')")
    @PostMapping("/approve-examination")
    public ResponseEntity<LabExamination> labExamination(@RequestBody ApproveLabExaminationReq req) {
        return ResponseEntity.ok(labExaminationService.approveLabExamination(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_LAB_SUPER')")
    @PostMapping("/reject-examination")
    public ResponseEntity<LabExamination> labExamination(@RequestBody RejectLabExaminationReq req) {
        return ResponseEntity.ok(labExaminationService.rejectLabExamination(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_LAB_SUPER')")
    @GetMapping("/search-examination")
    public ResponseEntity<?> searchLabExamination(@RequestBody LabExaminationSearchReq req) {
        return ResponseEntity.ok(labExaminationService.supervisorSearchLabExamination(req));
    }

}
