package com.helpet.service.appointment.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetTime;

@Getter
@AllArgsConstructor
public class TimeSlotResponse {
    private final Integer id;

    private final OffsetTime startTime;

    private final OffsetTime endTime;
}