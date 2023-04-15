package com.helpet.service.appointment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateAppointmentConclusionRequest {
    @NotBlank(message = "{validations.not-blank.diagnosis-cannot-be-blank}")
    private String diagnosis;

    @NotBlank(message = "{validations.not-blank.recommendations-cannot-be-blank}")
    private String recommendations;
}
