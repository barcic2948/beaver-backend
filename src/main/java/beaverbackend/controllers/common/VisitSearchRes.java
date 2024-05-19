package beaverbackend.controllers.common;

import beaverbackend.jpa.model.Doctor;
import beaverbackend.jpa.model.Patient;
import beaverbackend.jpa.model.Visit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class VisitSearchRes {
    @JsonProperty("visit")
    @JsonIgnoreProperties({"patient", "selectedDoctor", "receptionist"})
    private Visit visit;
    @JsonProperty("selectedPatient")
    @JsonIgnoreProperties({"visitList"})
    private Patient patient;
    @JsonProperty("selectedDoctor")
    @JsonIgnoreProperties({"visitList"})
    private Doctor doctor;
}
