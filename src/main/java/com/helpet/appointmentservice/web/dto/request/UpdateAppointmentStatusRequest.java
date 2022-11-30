package com.helpet.appointmentservice.web.dto.request;

import com.helpet.appointmentservice.store.model.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateAppointmentStatusRequest {
    private final AppointmentStatus status;
}
