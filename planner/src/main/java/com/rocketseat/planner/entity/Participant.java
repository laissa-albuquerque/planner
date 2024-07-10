package com.rocketseat.planner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed;
    @Column(name = "owner_name", nullable = false)
    private String participantName;
    @Column(name = "owner_email", nullable = false)
    private String participantEmail;
}
