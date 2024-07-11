package com.rocketseat.planner.repository;

import com.rocketseat.planner.entity.Participant;
import com.rocketseat.planner.entity.response.ParticipantDataResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    List<ParticipantDataResponse> findByTripId(UUID tripId);
}
