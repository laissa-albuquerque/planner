package com.rocketseat.planner.service;

import com.rocketseat.planner.entity.Activity;
import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.entity.request.ActivityRequestPayload;
import com.rocketseat.planner.entity.response.ActivityDataResponse;
import com.rocketseat.planner.entity.response.ActivityResponse;
import com.rocketseat.planner.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public ActivityResponse saveActivity(ActivityRequestPayload payload, Trip trip) {
        Activity activity = new Activity(payload.activityName(), payload.date(), trip);
        this.activityRepository.save(activity);

        return new ActivityResponse(activity.getId());
    }

    public List<ActivityDataResponse> getAllActivities(UUID tripId) {
        return activityRepository.findByTripId(tripId);
    }
}
