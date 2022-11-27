package com.helpet.appointmentservice.web.mapper;

import com.helpet.appointmentservice.store.model.TimeSlot;
import com.helpet.appointmentservice.web.dto.response.TimeSlotResponse;
import com.helpet.web.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimeSlotResponseMapper extends EntityMapper<TimeSlot, TimeSlotResponse> {
}
