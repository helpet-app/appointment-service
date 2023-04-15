package com.helpet.service.appointment.controller;

import com.helpet.service.appointment.mapper.TimeSlotMapper;
import com.helpet.service.appointment.service.VetScheduleService;
import com.helpet.service.appointment.storage.model.TimeSlot;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequestMapping("/vets/{vet-id}")
@RestController
public class VetScheduleController {
    private final VetScheduleService vetScheduleService;

    private final TimeSlotMapper timeSlotMapper;

    @Autowired
    public VetScheduleController(VetScheduleService vetScheduleService, TimeSlotMapper timeSlotMapper) {
        this.vetScheduleService = vetScheduleService;
        this.timeSlotMapper = timeSlotMapper;
    }

    @GetMapping("/free-time-slots")
    public ResponseEntity<ResponseBody> getFreeTimeSlots(@PathVariable("vet-id") UUID vetId, @RequestParam("date") LocalDate date) {
        List<TimeSlot> freeTimeSlots = vetScheduleService.getFreeTimeSlotsByDate(vetId, date);
        ResponseBody responseBody = new SuccessfulResponseBody<>(timeSlotMapper.mapCollection(freeTimeSlots));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
