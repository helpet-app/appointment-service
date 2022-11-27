package com.helpet.appointmentservice.web.controller;

import com.helpet.appointmentservice.service.AppointmentService;
import com.helpet.appointmentservice.store.model.Appointment;
import com.helpet.appointmentservice.web.mapper.AppointmentResponseMapper;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PetController {
    private final AppointmentService appointmentService;

    private final AppointmentResponseMapper appointmentResponseMapper;

    @Autowired
    public PetController(AppointmentService appointmentService, AppointmentResponseMapper appointmentResponseMapper) {
        this.appointmentService = appointmentService;
        this.appointmentResponseMapper = appointmentResponseMapper;
    }

    @RolesAllowed(Role.USER)
    @GetMapping("/user/families/{family-id}/pets/{pet-id}/appointments")
    public ResponseEntity<ResponseBody> getAppointments(JwtAuthenticationToken jwtAuthenticationToken,
                                                        @PathVariable("family-id") UUID familyId,
                                                        @PathVariable("pet-id") UUID petId,
                                                        Pageable pageable) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Page<Appointment> appointmentsPage = appointmentService.getPetAppointments(userId,
                                                                                   familyId,
                                                                                   petId,
                                                                                   pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(appointmentResponseMapper.mapToDtoPage(appointmentsPage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @RolesAllowed(Role.USER)
    @GetMapping("/user/families/{family-id}/pets/{pet-id}/appointments/{appointment-id}")
    public ResponseEntity<ResponseBody> getAppointment(JwtAuthenticationToken jwtAuthenticationToken,
                                                       @PathVariable("family-id") UUID familyId,
                                                       @PathVariable("pet-id") UUID petId,
                                                       @PathVariable("appointment-id") UUID appointmentId) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Appointment appointment = appointmentService.getPetAppointment(userId,
                                                                       familyId,
                                                                       petId,
                                                                       appointmentId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(appointmentResponseMapper.mapToDto(appointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
