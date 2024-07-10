package com.rocketseat.planner.entity.request;

import java.util.List;

public record TripRequestPayload(String destination, String startsAT, String endsAT, List<String> emails_to_invite, String ownerName, String ownerEmail) {}
