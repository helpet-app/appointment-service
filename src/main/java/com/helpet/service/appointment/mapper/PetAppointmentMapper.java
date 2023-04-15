package com.helpet.service.appointment.mapper;

import com.helpet.service.appointment.storage.model.Appointment;
import com.helpet.service.appointment.dto.response.PetAppointmentResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetAppointmentMapper extends ClassMapper<Appointment, PetAppointmentResponse> {
}
