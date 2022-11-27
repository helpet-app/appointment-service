package com.helpet.appointmentservice.web.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class AppointmentResponse {
    private UUID id;

    private OffsetDateTime createdAt;

    private OffsetDateTime scheduledAt;

    private String problem;

    private VetResponse vet;

    private UserResponse client;

    private PetResponse pet;
}