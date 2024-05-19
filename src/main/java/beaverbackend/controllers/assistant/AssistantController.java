package beaverbackend.controllers.assistant;

import beaverbackend.controllers.common.LabExaminationSearchReq;
import beaverbackend.jpa.model.LabExamination;
import beaverbackend.service.LabExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assistant")
@RequiredArgsConstructor
public class AssistantController {

    private final LabExaminationService labExaminationService;

    @PreAuthorize("hasAuthority('SCOPE_LAB_ASSISTANT')")
    @PostMapping("/complete-examination")
    public ResponseEntity<LabExamination> completeLabExamination(@RequestBody CompleteLabExaminationReq req) {
        return ResponseEntity.ok(labExaminationService.completeLabExamination(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_LAB_ASSISTANT')")
    @PostMapping("/cancel-examination")
    public ResponseEntity<LabExamination> cancelLabExamination(@RequestBody CancelLabExaminationReq req) {
        return ResponseEntity.ok(labExaminationService.cancelLabExamination(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_LAB_ASSISTANT')")
    @GetMapping("/search-examination")
    public ResponseEntity<List<LabExamination>> searchLabExamination(@RequestBody LabExaminationSearchReq req) {
        return ResponseEntity.ok(labExaminationService.assistantSearchLabExamination(req));
    }

}
