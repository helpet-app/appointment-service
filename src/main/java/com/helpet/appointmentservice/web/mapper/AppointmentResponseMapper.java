package com.helpet.appointmentservice.web.mapper;

import com.helpet.appointmentservice.store.model.Appointment;
import com.helpet.appointmentservice.web.dto.response.AppointmentResponse;
import com.helpet.web.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentResponseMapper extends EntityMapper<Appointment, AppointmentResponse> {
}
