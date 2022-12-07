package com.helpet.service.appointment.web.mapper;

import com.helpet.service.appointment.store.model.VetScheduleSlot;
import com.helpet.service.appointment.web.dto.response.VetScheduleSlotResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VetScheduleSlotMapper extends ClassMapper<VetScheduleSlot, VetScheduleSlotResponse> {
}