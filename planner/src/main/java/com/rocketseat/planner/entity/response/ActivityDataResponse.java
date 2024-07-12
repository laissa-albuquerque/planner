package com.rocketseat.planner.entity.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityDataResponse(UUID id, String activityName, LocalDateTime date) {}
