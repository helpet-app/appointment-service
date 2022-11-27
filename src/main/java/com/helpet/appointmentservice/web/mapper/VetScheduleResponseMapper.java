package com.helpet.appointmentservice.web.mapper;

import com.helpet.appointmentservice.store.model.VetSchedule;
import com.helpet.appointmentservice.web.dto.response.VetScheduleResponse;
import com.helpet.web.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VetScheduleResponseMapper extends EntityMapper<VetSchedule, VetScheduleResponse> {
}