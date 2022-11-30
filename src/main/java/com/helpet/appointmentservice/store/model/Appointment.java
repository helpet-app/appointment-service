package com.helpet.appointmentservice.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "appointments", indexes = {
        @Index(name = "non_emergency_appointments_pet_fkey", columnList = "pet_id"),
        @Index(name = "non_emergency_appointments_vet_fkey", columnList = "vet_id"),
        @Index(name = "non_emergency_appointments_client_fkey", columnList = "client_id")
})
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @NotNull
    @Column(name = "scheduled_at", nullable = false)
    private OffsetDateTime scheduledAt;

    @Column(name = "connection_link", length = Integer.MAX_VALUE)
    private String connectionLink;

    @NotNull
    @Column(name = "problem", nullable = false, length = Integer.MAX_VALUE)
    private String problem;

    @Column(name = "diagnosis", length = Integer.MAX_VALUE)
    private String diagnosis;

    @Column(name = "recommendations", length = Integer.MAX_VALUE)
    private String recommendations;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "vet_id", nullable = false)
    private Vet vet;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
}