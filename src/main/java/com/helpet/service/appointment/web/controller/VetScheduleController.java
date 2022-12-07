package com.helpet.service.appointment.web.controller;

import com.helpet.service.appointment.service.VetScheduleService;
import com.helpet.service.appointment.store.model.TimeSlot;
import com.helpet.service.appointment.store.model.VetScheduleSlot;
import com.helpet.service.appointment.web.mapper.TimeSlotMapper;
import com.helpet.service.appointment.web.mapper.VetScheduleSlotMapper;
import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class VetScheduleController {
    private final VetScheduleService vetScheduleService;

    private final VetScheduleSlotMapper vetScheduleSlotMapper;

    private final TimeSlotMapper timeSlotMapper;

    @Autowired
    public VetScheduleController(VetScheduleService vetScheduleService, VetScheduleSlotMapper vetScheduleSlotMapper, TimeSlotMapper timeSlotMapper) {
        this.vetScheduleService = vetScheduleService;
        this.vetScheduleSlotMapper = vetScheduleSlotMapper;
        this.timeSlotMapper = timeSlotMapper;
    }

    @RolesAllowed(Role.VET)
    @GetMapping("vet/schedule")
    public ResponseEntity<ResponseBody> getSchedule(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        List<VetScheduleSlot> vetSchedule = vetScheduleService.getSchedule(vetId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetScheduleSlotMapper.mapCollection(vetSchedule));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @RolesAllowed(Role.USER)
    @GetMapping("/vets/{vet-id}/free-time-slots")
    public ResponseEntity<ResponseBody> getFreeTimeSlots(@PathVariable("vet-id") UUID vetId, @RequestParam("date") LocalDate date) {
        List<TimeSlot> freeTimeSlots = vetScheduleService.getFreeTimeSlotsByDate(vetId, date);
        ResponseBody responseBody = new SuccessfulResponseBody<>(timeSlotMapper.mapCollection(freeTimeSlots));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
