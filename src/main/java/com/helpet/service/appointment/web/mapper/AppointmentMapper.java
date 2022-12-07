package com.helpet.service.appointment.web.mapper;

import com.helpet.service.appointment.store.model.Appointment;
import com.helpet.service.appointment.web.dto.response.AppointmentResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper extends ClassMapper<Appointment, AppointmentResponse> {
}
