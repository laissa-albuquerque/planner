package com.rocketseat.planner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "participants")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "participant_name", nullable = false)
    private String participantName;
    @Column(name = "participant_email", nullable = false)
    private String participantEmail;
    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed;
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    public Participant(String participantEmail, Trip trip) {
        this.isConfirmed = false;
        this.participantName = "";
        this.participantEmail = participantEmail;
        this.trip = trip;
    }
}
