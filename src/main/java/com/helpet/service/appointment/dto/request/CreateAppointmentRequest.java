package com.helpet.service.appointment.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateAppointmentRequest {
    @NotNull(message = "{validations.not-null.date-cannot-be-null}")
    @Future(message = "{validations.future.date-cannot-be-in-past-or-present}")
    private LocalDate date;

    @NotNull(message = "{validations.not-null.time-slot-id-cannot-be-null}")
    private Integer timeSlotId;

    @NotBlank(message = "{validations.not-blank.problem-cannot-be-blank}")
    private String problem;

    @NotNull(message = "{validations.not-null.vet-id-cannot-be-null}")
    private UUID vetId;

    @NotNull(message = "{validations.not-null.pet-id-cannot-be-null}")
    private UUID petId;
}
