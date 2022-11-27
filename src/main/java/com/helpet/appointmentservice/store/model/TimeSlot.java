package com.helpet.appointmentservice.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetTime;

@Getter
@Setter
@Entity
@Table(name = "time_slots", indexes = {
        @Index(name = "time_slots_start_time_end_time_key", columnList = "start_time, end_time", unique = true),
        @Index(name = "time_slots_end_time_key", columnList = "end_time", unique = true),
        @Index(name = "time_slots_start_time_key", columnList = "start_time", unique = true)
})
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private OffsetTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private OffsetTime endTime;
}