package com.helpet.appointmentservice.web.dto.response;

import com.helpet.appointmentservice.store.model.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class DetailedAppointmentResponse {
    private final UUID id;

    private final OffsetDateTime createdAt;

    private final OffsetDateTime scheduledAt;

    private final String connectionLink;

    private final String problem;

    private final String diagnosis;

    private final String recommendations;

    private final AppointmentStatus status;

    private final DetailedVetResponse vet;

    private final DetailedUserResponse client;

    private final PetResponse pet;
}
