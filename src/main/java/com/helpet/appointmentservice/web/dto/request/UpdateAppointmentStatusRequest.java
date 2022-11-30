package com.helpet.appointmentservice.web.dto.request;

import com.helpet.appointmentservice.store.model.AppointmentStatus;
import lombok.Data;

@Data
public class UpdateAppointmentStatusRequest {
    private AppointmentStatus status;
}
