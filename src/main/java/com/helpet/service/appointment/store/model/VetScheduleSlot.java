package com.helpet.service.appointment.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "vet_schedule", indexes = {
        @Index(name = "vet_schedule_vet_fkey", columnList = "vet_id")
})
public class VetScheduleSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot;

    @NotNull
    @Column(name = "monday", nullable = false)
    private Boolean monday = false;

    @NotNull
    @Column(name = "tuesday", nullable = false)
    private Boolean tuesday = false;

    @NotNull
    @Column(name = "wednesday", nullable = false)
    private Boolean wednesday = false;

    @NotNull
    @Column(name = "thursday", nullable = false)
    private Boolean thursday = false;

    @NotNull
    @Column(name = "friday", nullable = false)
    private Boolean friday = false;

    @NotNull
    @Column(name = "saturday", nullable = false)
    private Boolean saturday = false;

    @NotNull
    @Column(name = "sunday", nullable = false)
    private Boolean sunday = false;
}