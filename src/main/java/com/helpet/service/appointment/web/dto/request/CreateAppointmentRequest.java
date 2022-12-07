package com.helpet.service.appointment.web.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateAppointmentRequest {
    private LocalDate date;

    private Integer timeSlotId;

    private String problem;

    private UUID vetId;

    private UUID petId;
}
