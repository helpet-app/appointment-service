package com.helpet.appointmentservice.web.controller;

import com.helpet.appointmentservice.service.VetService;
import com.helpet.appointmentservice.store.model.TimeSlot;
import com.helpet.appointmentservice.web.mapper.TimeSlotResponseMapper;
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
public class VetCommonController {
    private final VetService vetService;

    private final TimeSlotResponseMapper timeSlotResponseMapper;

    @Autowired
    public VetCommonController(VetService vetService, TimeSlotResponseMapper timeSlotResponseMapper) {
        this.vetService = vetService;
        this.timeSlotResponseMapper = timeSlotResponseMapper;
    }

    @GetMapping("/free-time-slots")
    public ResponseEntity<ResponseBody> getFreeTimeSlots(@PathVariable("vet-id") UUID vetId,
                                                         @RequestParam("date") LocalDate date) {
        List<TimeSlot> freeTimeSlots = vetService.getFreeTimeSlotsByDate(vetId, date);
        ResponseBody responseBody = new SuccessfulResponseBody<>(timeSlotResponseMapper.mapToDtoCollection(freeTimeSlots));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
