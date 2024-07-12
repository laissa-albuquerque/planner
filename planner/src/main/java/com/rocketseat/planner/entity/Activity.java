package com.rocketseat.planner.entity;

import com.rocketseat.planner.entity.request.ActivityRequestPayload;
import com.rocketseat.planner.entity.request.TripRequestPayload;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "activity_name", nullable = false)
    private String activityName;
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    public Activity(String activityName, String date, Trip trip) {
        this.activityName = activityName;
        this.date = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
        this.isCompleted = false;
        this.trip = trip;
    }

}
