package com.helpet.service.appointment.web.dto.request;

import com.helpet.service.appointment.store.model.AppointmentStatus;
import lombok.Data;

@Data
public class UpdateAppointmentStatusRequest {
    private AppointmentStatus status;
}
