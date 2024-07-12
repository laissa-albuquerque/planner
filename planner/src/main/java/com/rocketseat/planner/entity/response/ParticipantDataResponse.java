package com.rocketseat.planner.entity.response;

import java.util.UUID;

public record ParticipantDataResponse(UUID id, String participantName, String participantEmail, Boolean isConfirmed) {
}
