package com.helpet.appointmentservice.web.mapper;

import com.helpet.appointmentservice.store.model.VetScheduleSlot;
import com.helpet.appointmentservice.web.dto.response.VetScheduleSlotResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VetScheduleSlotMapper extends ClassMapper<VetScheduleSlot, VetScheduleSlotResponse> {
}