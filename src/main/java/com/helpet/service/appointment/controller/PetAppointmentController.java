package com.helpet.service.appointment.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.appointment.mapper.PetAppointmentMapper;
import com.helpet.service.appointment.service.PetAppointmentService;
import com.helpet.service.appointment.storage.model.Appointment;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PetAppointmentController {
    private final PetAppointmentService petAppointmentService;

    private final PetAppointmentMapper petAppointmentMapper;

    @Autowired
    public PetAppointmentController(PetAppointmentService petAppointmentService, PetAppointmentMapper petAppointmentMapper) {
        this.petAppointmentService = petAppointmentService;
        this.petAppointmentMapper = petAppointmentMapper;
    }

    @GetMapping("/user/pets/{pet-id}/appointments")
    public ResponseEntity<ResponseBody> getAppointments(@PathVariable("pet-id") UUID petId,
                                                        Pageable pageable,
                                                        JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Page<Appointment> appointmentsPage = petAppointmentService.getAppointments(userId, petId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petAppointmentMapper.mapPage(appointmentsPage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/user/pets/{pet-id}/appointments/{appointment-id}")
    public ResponseEntity<ResponseBody> getAppointment(@PathVariable("pet-id") UUID petId,
                                                       @PathVariable("appointment-id") UUID appointmentId,
                                                       JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Appointment appointment = petAppointmentService.getAppointment(userId, petId, appointmentId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petAppointmentMapper.map(appointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @RolesAllowed(Role.VET)
    @GetMapping("/pets/{pet-id}/appointments")
    public ResponseEntity<ResponseBody> getAppointments(@PathVariable("pet-id") UUID petId, Pageable pageable) {
        Page<Appointment> appointmentsPage = petAppointmentService.getAppointments(petId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petAppointmentMapper.mapPage(appointmentsPage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @RolesAllowed(Role.VET)
    @GetMapping("/pets/{pet-id}/appointments/{appointment-id}")
    public ResponseEntity<ResponseBody> getAppointment(@PathVariable("pet-id") UUID petId, @PathVariable("appointment-id") UUID appointmentId) {
        Appointment appointment = petAppointmentService.getAppointment(petId, appointmentId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petAppointmentMapper.map(appointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
