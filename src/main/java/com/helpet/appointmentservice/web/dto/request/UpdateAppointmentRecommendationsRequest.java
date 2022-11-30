package com.helpet.appointmentservice.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateAppointmentRecommendationsRequest {
    private final String recommendations;
}
