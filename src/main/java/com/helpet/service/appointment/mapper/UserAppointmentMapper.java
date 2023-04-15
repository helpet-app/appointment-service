package com.helpet.service.appointment.mapper;

import com.helpet.service.appointment.dto.response.UserAppointmentResponse;
import com.helpet.service.appointment.storage.model.Appointment;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAppointmentMapper extends ClassMapper<Appointment, UserAppointmentResponse> {
}
