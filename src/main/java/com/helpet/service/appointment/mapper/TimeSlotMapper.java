package com.helpet.service.appointment.mapper;

import com.helpet.service.appointment.storage.model.TimeSlot;
import com.helpet.service.appointment.dto.response.TimeSlotResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimeSlotMapper extends ClassMapper<TimeSlot, TimeSlotResponse> {
}
