package com.helpet.service.appointment.web.mapper;

import com.helpet.service.appointment.store.model.Appointment;
import com.helpet.service.appointment.web.dto.response.DetailedAppointmentResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DetailedAppointmentMapper extends ClassMapper<Appointment, DetailedAppointmentResponse> {
}
