package com.rocketseat.planner.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {


    public void registerParticipantToEvent(List<String> participantsToInvite, UUID id){}

    public void triggerConfirmationEmailToParticipants(UUID tripID){}
}
