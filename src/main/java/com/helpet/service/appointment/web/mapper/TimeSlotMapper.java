package com.helpet.service.appointment.web.mapper;

import com.helpet.service.appointment.store.model.TimeSlot;
import com.helpet.service.appointment.web.dto.response.TimeSlotResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimeSlotMapper extends ClassMapper<TimeSlot, TimeSlotResponse> {
}
