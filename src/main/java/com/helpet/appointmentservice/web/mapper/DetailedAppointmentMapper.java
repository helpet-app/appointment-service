package com.helpet.appointmentservice.web.mapper;

import com.helpet.appointmentservice.store.model.Appointment;
import com.helpet.appointmentservice.web.dto.response.DetailedAppointmentResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DetailedAppointmentMapper extends ClassMapper<Appointment, DetailedAppointmentResponse> {
}
