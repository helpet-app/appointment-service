package com.helpet.service.appointment.dto.response;

import com.helpet.service.appointment.storage.model.AppointmentStatus;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class PetAppointmentResponse {
    private UUID id;

    private String problem;

    private String connectionLink;

    private String diagnosis;

    private String recommendations;

    private AppointmentStatus status;

    private OffsetDateTime scheduledAt;

    private OffsetDateTime createdAt;

    private VetResponse vet;

    private UserResponse client;
}