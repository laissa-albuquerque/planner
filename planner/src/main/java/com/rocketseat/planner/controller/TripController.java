package com.rocketseat.planner.controller;

import com.rocketseat.planner.entity.Link;
import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.entity.request.*;
import com.rocketseat.planner.entity.response.*;
import com.rocketseat.planner.repository.TripRepository;
import com.rocketseat.planner.service.ActivityService;
import com.rocketseat.planner.service.LinkService;
import com.rocketseat.planner.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LinkService linkService;

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

        if (trip.isPresent()) {
            Trip tripToUpdate = trip.get();
            ParticipantInviteResponse participantResponse = this.participantService.registerParticipantToEvent(payload.participantEmail(), tripToUpdate);

            if (tripToUpdate.getIsConfirmed())
                this.participantService.triggerConfirmationEmailToParticipant(payload.participantEmail());

            return ResponseEntity.ok(participantResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantDataResponse>> getParticipantsById(@PathVariable UUID id) {
        Optional<Trip> tripExist = tripRepository.findById(id);

        if (tripExist.isPresent()) {
            return ResponseEntity.ok(participantService.getAllParticipantsFromEventId(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/activity")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityRequestPayload payload) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip tripToUpdate = trip.get();
            ActivityResponse activity = this.activityService.saveActivity(payload, tripToUpdate);

            return ResponseEntity.ok(activity);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDataResponse>> getActivitiesById(@PathVariable UUID id) {
        Optional<Trip> tripExist = tripRepository.findById(id);

        if (tripExist.isPresent()) {
            return ResponseEntity.ok(activityService.getAllActivities(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/link")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable UUID id, @RequestBody LinkRequestPayload payload) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip tripToUpdate = trip.get();
            LinkResponse newLink = this.linkService.saveLink(payload, tripToUpdate);

            return ResponseEntity.ok(newLink);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkDataResponse>> getLinksById(@PathVariable UUID id) {
        Optional<Trip> tripExist = tripRepository.findById(id);

        if (tripExist.isPresent()) {
            return ResponseEntity.ok(linkService.getAllLinks(id));
        }
        return ResponseEntity.notFound().build();
    }
}
