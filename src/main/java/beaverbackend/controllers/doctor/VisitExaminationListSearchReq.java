package beaverbackend.controllers.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VisitExaminationListSearchReq {
    @JsonProperty("visitId")
    private Long visitId;
    @JsonProperty("examinationType")
    private String examinationType;
}
