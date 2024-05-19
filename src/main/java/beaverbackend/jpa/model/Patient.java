package beaverbackend.jpa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "patient")
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @Column(name = "insurance_id")
    private String insuranceId;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person", referencedColumnName = "nationalIDNumber", nullable = false)
    private Person person;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Visit> visitList;

    public Patient(@NonNull Person person, String insuranceId) {
        this.person = person;
        this.insuranceId = insuranceId;
    }
}
