package com.rocketseat.planner.repository;

import com.rocketseat.planner.entity.Activity;
import com.rocketseat.planner.entity.response.ActivityDataResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {

    List<ActivityDataResponse> findByTripId(UUID id);

}
