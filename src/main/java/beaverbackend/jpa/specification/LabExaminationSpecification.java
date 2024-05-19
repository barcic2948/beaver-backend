package beaverbackend.jpa.specification;

import beaverbackend.enums.LaboratoryStatusEnum;
import beaverbackend.enums.RightsLevelEnum;
import beaverbackend.jpa.model.LabExamination;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class LabExaminationSpecification {

    public static Specification<LabExamination> searchSpecification(LaboratoryStatusEnum status, RightsLevelEnum rightsLevel, Long assistantId, String examinationCode) {
        return ((root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (examinationCode != null && !examinationCode.isEmpty())
                predicates.add(builder.and(builder.equal(root.get("examinationDictionary").get("code"), examinationCode)));
            if (rightsLevel != null)
                predicates.add(builder.and(builder.equal(root.get("examinationDictionary").get("rightsLevel"), rightsLevel)));
            if (status != null)
                predicates.add(builder.and(builder.equal(root.get("status"), status)));
            if (assistantId != null) {
                predicates.add(builder.and(builder.equal(root.get("labAssistant").get("id"), assistantId)));
            }

            query.orderBy(builder.asc(root.get("orderedDateTime")));
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }

}
