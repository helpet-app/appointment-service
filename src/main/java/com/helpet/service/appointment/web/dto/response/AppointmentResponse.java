package com.helpet.service.appointment.web.dto.response;

import com.helpet.service.appointment.store.model.AppointmentStatus;
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