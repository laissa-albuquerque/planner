package com.rocketseat.planner.entity;

import com.rocketseat.planner.entity.request.TripRequestPayload;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String destination;
    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startsAT;
    @Column(name = "ends_at", nullable = false)
    private LocalDateTime endsAT;
    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed;
    @Column(name = "owner_name", nullable = false)
    private String ownerName;
    @Column(name = "owner_email", nullable = false)
    private String ownerEmail;

    public Trip(TripRequestPayload data) {
        this.destination = data.destination();
        this.isConfirmed = false;
        this.startsAT = LocalDateTime.parse(data.startsAT(), DateTimeFormatter.ISO_DATE_TIME);
        this.endsAT = LocalDateTime.parse(data.endsAT(), DateTimeFormatter.ISO_DATE_TIME);
        this.ownerName = data.ownerName();
        this.ownerEmail = data.ownerEmail();
    }

}
