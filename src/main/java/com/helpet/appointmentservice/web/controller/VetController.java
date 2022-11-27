package com.helpet.appointmentservice.web.controller;

import com.helpet.appointmentservice.service.AppointmentService;
import com.helpet.appointmentservice.service.VetService;
import com.helpet.appointmentservice.store.model.Appointment;
import com.helpet.appointmentservice.store.model.VetSchedule;
import com.helpet.appointmentservice.web.mapper.AppointmentResponseMapper;
import com.helpet.appointmentservice.web.mapper.VetScheduleResponseMapper;
import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RolesAllowed(Role.VET)
@RequestMapping("/vet")
@RestController
public class VetController {
    private final VetService vetService;

    private final AppointmentService appointmentService;

    private final VetScheduleResponseMapper vetScheduleResponseMapper;

    private final AppointmentResponseMapper appointmentResponseMapper;

    @Autowired
    public VetController(VetService vetService,
                         AppointmentService appointmentService,
                         VetScheduleResponseMapper vetScheduleResponseMapper,
                         AppointmentResponseMapper appointmentResponseMapper) {
        this.vetService = vetService;
        this.appointmentService = appointmentService;
        this.vetScheduleResponseMapper = vetScheduleResponseMapper;
        this.appointmentResponseMapper = appointmentResponseMapper;
    }

    @GetMapping("/schedule")
    public ResponseEntity<ResponseBody> getSchedule(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        List<VetSchedule> vetSchedule = vetService.getSchedule(vetId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetScheduleResponseMapper.mapToDtoCollection(vetSchedule));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/appointments")
    public ResponseEntity<ResponseBody> getAppointments(JwtAuthenticationToken jwtAuthenticationToken, Pageable pageable) {
        UUID vetId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Page<Appointment> appointmentsPage = appointmentService.getVetAppointments(vetId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(appointmentResponseMapper.mapToDtoPage(appointmentsPage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/appointments/{appointment-id}")
    public ResponseEntity<ResponseBody> getAppointment(JwtAuthenticationToken jwtAuthenticationToken,
                                                       @PathVariable("appointment-id") UUID appointmentId) {
        UUID vetId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Appointment appointment = appointmentService.getVetAppointment(vetId, appointmentId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(appointmentResponseMapper.mapToDto(appointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    /*@GetMapping("/appointments/future")
    public ResponseEntity<ResponseBody> getFutureAppointments(JwtAuthenticationToken jwtAuthenticationToken, Pageable pageable) {
        UUID vetId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Page<Appointment> appointmentsPage = appointmentService.getVetFutureAppointments(vetId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(appointmentResponseMapper.mapToDtoPage(appointmentsPage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }*/
}
