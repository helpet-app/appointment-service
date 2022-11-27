package com.helpet.appointmentservice.web.dto.response;

import lombok.Data;

import java.time.OffsetTime;

@Data
public class TimeSlotResponse {
    private Integer id;

    private OffsetTime startTime;

    private OffsetTime endTime;
}