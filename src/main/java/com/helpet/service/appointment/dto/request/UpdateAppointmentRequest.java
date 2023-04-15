package com.helpet.service.appointment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateAppointmentRequest {
    @NotBlank(message = "{validations.not-blank.problem-cannot-be-blank}")
    private String problem;
}
