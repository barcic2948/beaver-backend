package beaverbackend.jpa.specification;

import beaverbackend.controllers.common.VisitSearchReq;
import beaverbackend.jpa.model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VisitSpecification {
    public static Specification<Visit> searchSpecification(VisitSearchReq req) {
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            Join<Visit, Doctor> doctorJoin = root.join("selectedDoctor", JoinType.INNER);
            Join<Doctor, ClinicStaff> staffJoin = doctorJoin.join("clinicStaff", JoinType.INNER);
            Join<ClinicStaff, Person> personDoctorJoin = staffJoin.join("person", JoinType.INNER);
            Join<Visit, Patient> patientJoin = root.join("patient", JoinType.INNER);
            Join<Patient, Person> personPatientJoin = patientJoin.join("person", JoinType.INNER);

            if (req.getPatientFirstName() != null && !req.getPatientFirstName().isEmpty())
                predicates.add(builder.and(builder.like(personPatientJoin.get("firstName"), "%" + req.getPatientFirstName() + "%")));
            if (req.getPatientLastName() != null && !req.getPatientLastName().isEmpty())
                predicates.add(builder.and(builder.like(personPatientJoin.get("lastName"), "%" + req.getPatientLastName() + "%")));
            if (req.getPatientInsuranceId() != null && !req.getPatientInsuranceId().isEmpty())
                predicates.add(builder.and(builder.equal(patientJoin.get("insuranceId"), req.getPatientInsuranceId())));

            if (req.getDoctorFirstName() != null && !req.getDoctorFirstName().isEmpty())
                predicates.add(builder.and(builder.like(personDoctorJoin.get("firstName"), "%" + req.getDoctorFirstName() + "%")));
            if (req.getDoctorLastName() != null && !req.getDoctorLastName().isEmpty())
                predicates.add(builder.and(builder.like(personDoctorJoin.get("lastName"), "%" + req.getDoctorLastName() + "%")));
            if (req.getDoctorNpwzId() != null && !req.getDoctorNpwzId().isEmpty())
                predicates.add(builder.and(builder.equal(root.get("npwzId"), req.getDoctorNpwzId())));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
