package com.rocketseat.planner.service;

import com.rocketseat.planner.entity.Participant;
import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.entity.response.ParticipantDataResponse;
import com.rocketseat.planner.entity.response.ParticipantInviteResponse;
import com.rocketseat.planner.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip){
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

        this.participantRepository.saveAll(participants);
    }

    public ParticipantInviteResponse registerParticipantToEvent(String email, Trip trip) {
        Participant participant = new Participant(email, trip);
        this.participantRepository.save(participant);

        return new ParticipantInviteResponse(participant.getId());
    }

    public List<ParticipantDataResponse> getAllParticipantsFromEventId(UUID id) {
        return participantRepository.findByTripId(id);
    }

    public void triggerConfirmationEmailToParticipants(UUID tripID){}

    public void triggerConfirmationEmailToParticipant(String email){}
}
