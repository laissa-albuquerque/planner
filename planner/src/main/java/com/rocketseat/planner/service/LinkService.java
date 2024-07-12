package com.rocketseat.planner.service;

import com.rocketseat.planner.entity.Link;
import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.entity.request.LinkRequestPayload;
import com.rocketseat.planner.entity.response.LinkDataResponse;
import com.rocketseat.planner.entity.response.LinkResponse;
import com.rocketseat.planner.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public LinkResponse saveLink(LinkRequestPayload payload, Trip trip) {
        Link newLink = new Link(payload.title(), payload.url(), trip);
        this.linkRepository.save(newLink);

        return new LinkResponse(newLink.getId());
    }

    public List<LinkDataResponse> getAllLinks(UUID tripId) {
        return this.linkRepository.findByTripId(tripId);
    }
}
