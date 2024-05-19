package beaverbackend.controllers.receptionist;

import beaverbackend.controllers.common.VisitSearchReq;
import beaverbackend.controllers.common.VisitSearchRes;
import beaverbackend.jpa.model.Doctor;
import beaverbackend.jpa.model.Patient;
import beaverbackend.jpa.model.Visit;
import beaverbackend.service.DoctorService;
import beaverbackend.service.PatientService;
import beaverbackend.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receptionist")
@RequiredArgsConstructor
public class ReceptionistController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final VisitService visitService;

    @PreAuthorize("hasAuthority('SCOPE_RECEPTIONIST')")
    @GetMapping("/search-patient")
    public ResponseEntity<List<Patient>> searchPatient(@RequestBody PatientSearchReq req) {
        return ResponseEntity.ok(patientService.searchPatients(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_RECEPTIONIST')")
    @GetMapping("/search-doctor")
    public ResponseEntity<List<Doctor>> searchDoctor(@RequestBody DoctorSearchReq req) {
        return ResponseEntity.ok(doctorService.searchDoctors(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_RECEPTIONIST')")
    @PostMapping("/create-visit")
    public ResponseEntity<Visit> scheduleVisit(@RequestBody VisitCreateReq req) {
        return ResponseEntity.ok(visitService.createNewVisit(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_RECEPTIONIST')")
    @GetMapping("/search-visit")
    public ResponseEntity<List<VisitSearchRes>> searchVisit(@RequestBody VisitSearchReq req) {
        List<Visit> searchResult = visitService.searchVisits(req);
        List<VisitSearchRes> result = searchResult.stream()
                .map(visit -> new VisitSearchRes(visit, visit.getPatient(), visit.getSelectedDoctor())).toList();
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAuthority('SCOPE_RECEPTIONIST')")
    @PostMapping("/cancel-visit")
    public ResponseEntity<Visit> cancelVisit(@RequestBody VisitCancelReq req) {
        return ResponseEntity.ok(visitService.cancelVisit(req.getVisitId()));
    }
}
