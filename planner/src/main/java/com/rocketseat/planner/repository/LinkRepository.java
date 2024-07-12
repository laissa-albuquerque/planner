package com.rocketseat.planner.repository;

import com.rocketseat.planner.entity.Link;
import com.rocketseat.planner.entity.response.LinkDataResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LinkRepository extends JpaRepository<Link, UUID> {

    List<LinkDataResponse> findByTripId(UUID id);
}
