package com.rocketseat.planner.controller;

import com.rocketseat.planner.entity.Participant;
import com.rocketseat.planner.entity.request.ParticipantRequestPayload;
import com.rocketseat.planner.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmTripParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload) {
        Optional<Participant> participant = this.participantRepository.findById(id);

        if (participant.isPresent()) {
            Participant participantToUpdate = participant.get();
            participantToUpdate.setIsConfirmed(true);
            participantToUpdate.setParticipantName(payload.participantName());

            this.participantRepository.save(participantToUpdate);

            return ResponseEntity.ok(participantToUpdate);
        }
        return ResponseEntity.notFound().build();
    }
}
