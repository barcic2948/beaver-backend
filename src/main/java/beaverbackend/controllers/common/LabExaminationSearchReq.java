package beaverbackend.controllers.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LabExaminationSearchReq {
    @JsonProperty("status")
    private String status;
    @JsonProperty("examinationCode")
    private String examinationCode;
    @JsonProperty("labAssistantId")
    private String labAssistantId;
    @JsonProperty("rightsLevel")
    private String rightsLevel;
}
