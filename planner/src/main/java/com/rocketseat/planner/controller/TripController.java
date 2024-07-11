package com.rocketseat.planner.controller;

import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.entity.request.ParticipantRequestPayload;
import com.rocketseat.planner.entity.request.TripPutRequestPayload;
import com.rocketseat.planner.entity.request.TripRequestPayload;
import com.rocketseat.planner.entity.response.ParticipantInviteResponse;
import com.rocketseat.planner.entity.response.TripCreateResponse;
import com.rocketseat.planner.repository.TripRepository;
import com.rocketseat.planner.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TripRepository tripRepository;

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) {
        Trip trip = new Trip(payload);
        this.tripRepository.save(trip);
        this.participantService.registerParticipantsToEvent(payload.emails_to_invite(), trip);

        return ResponseEntity.ok(new TripCreateResponse(trip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripsById(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);
        return trip.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripPutRequestPayload payload) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip tripToUpdate = trip.get();
            tripToUpdate.setStartsAT(LocalDateTime.parse(payload.startsAT(), DateTimeFormatter.ISO_DATE_TIME));
            tripToUpdate.setEndsAT(LocalDateTime.parse(payload.endsAT(), DateTimeFormatter.ISO_DATE_TIME));
            tripToUpdate.setDestination(payload.destination());

            this.tripRepository.save(tripToUpdate);

            return ResponseEntity.ok(tripToUpdate);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip tripToUpdate = trip.get();
            tripToUpdate.setIsConfirmed(true);

            this.tripRepository.save(tripToUpdate);
            this.participantService.triggerConfirmationEmailToParticipants(id);

            return ResponseEntity.ok(tripToUpdate);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/invites")
    public ResponseEntity<ParticipantInviteResponse> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if(trip.isPresent()) {
            Trip tripToUpdate = trip.get();
            ParticipantInviteResponse participantResponse =  this.participantService.registerParticipantToEvent(payload.participantEmail(), tripToUpdate);

            if(tripToUpdate.getIsConfirmed()) this.participantService.triggerConfirmationEmailToParticipant(payload.participantEmail());

            return ResponseEntity.ok(participantResponse);
        }
        return ResponseEntity.notFound().build();
    }
}
