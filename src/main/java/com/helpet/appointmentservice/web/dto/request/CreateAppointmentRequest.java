package com.helpet.appointmentservice.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateAppointmentRequest {
    private LocalDate date;

    private final Integer timeSlotId;

    private final String problem;

    private final UUID vetId;

    private final UUID petId;
}
