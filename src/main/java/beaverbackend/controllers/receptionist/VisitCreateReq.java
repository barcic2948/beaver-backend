package beaverbackend.controllers.receptionist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VisitCreateReq {
    @JsonProperty("description")
    private String description;
    @JsonProperty("doctorNpwzId")
    private String doctorNpwzId;
    @JsonProperty("patientInsuranceId")
    private String patientInsuranceId;
    @JsonProperty("scheduledDateTime")
    private String scheduledDateTime;
}
