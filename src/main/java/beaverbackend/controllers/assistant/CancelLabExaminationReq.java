package beaverbackend.controllers.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CancelLabExaminationReq {
    @JsonProperty("examinationId")
    private Long examinationId;
    @JsonProperty("cancellationReason")
    private String cancellationReason;
}
