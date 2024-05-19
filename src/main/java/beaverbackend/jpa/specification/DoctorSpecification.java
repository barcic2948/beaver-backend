package beaverbackend.jpa.specification;

import beaverbackend.controllers.receptionist.DoctorSearchReq;
import beaverbackend.jpa.model.ClinicStaff;
import beaverbackend.jpa.model.Doctor;
import beaverbackend.jpa.model.Person;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DoctorSpecification {

    public static Specification<Doctor> searchSpecification(DoctorSearchReq req) {
        return ((root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();
            Join<Doctor, ClinicStaff> staffJoin = root.join("clinicStaff", JoinType.INNER);
            Join<ClinicStaff, Person> personJoin = staffJoin.join("person", JoinType.INNER);

            if (req.getFirstName() != null && !req.getFirstName().isEmpty())
                predicates.add(builder.and(builder.like(personJoin.get("firstName"), "%" + req.getFirstName() + "%")));
            if (req.getLastName() != null && !req.getLastName().isEmpty())
                predicates.add(builder.and(builder.like(personJoin.get("lastName"), "%" + req.getLastName() + "%")));
            if (req.getNationalIdNumber() != null && !req.getNationalIdNumber().isEmpty())
                predicates.add(builder.and(builder.equal(personJoin.get("nationalIdNumber"), req.getNationalIdNumber())));
            if (req.getNpwzId() != null && !req.getNpwzId().isEmpty())
                predicates.add(builder.and(builder.equal(root.get("npwzId"), req.getNpwzId())));

            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }

}
