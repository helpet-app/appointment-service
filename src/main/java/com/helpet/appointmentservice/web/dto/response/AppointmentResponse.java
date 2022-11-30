package com.helpet.appointmentservice.web.dto.response;

import com.helpet.appointmentservice.store.model.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class AppointmentResponse {
    private final UUID id;

    private final OffsetDateTime scheduledAt;

    private final String problem;

    private final AppointmentStatus status;

    private final VetResponse vet;

    private final UserResponse client;

    private final PetResponse pet;
}