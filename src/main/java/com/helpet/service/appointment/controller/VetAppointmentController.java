package com.helpet.service.appointment.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.appointment.dto.request.UpdateAppointmentConclusionRequest;
import com.helpet.service.appointment.mapper.VetAppointmentMapper;
import com.helpet.service.appointment.service.VetAppointmentService;
import com.helpet.service.appointment.storage.model.Appointment;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RolesAllowed(Role.VET)
@RequestMapping("/vet/appointments")
@RestController
public class VetAppointmentController {
    private final VetAppointmentService vetAppointmentService;

    private final VetAppointmentMapper vetAppointmentMapper;

    @Autowired
    public VetAppointmentController(VetAppointmentService vetAppointmentService, VetAppointmentMapper vetAppointmentMapper) {
        this.vetAppointmentService = vetAppointmentService;
        this.vetAppointmentMapper = vetAppointmentMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getAppointments(Pageable pageable, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Page<Appointment> appointmentsPage = vetAppointmentService.getAppointments(vetId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetAppointmentMapper.mapPage(appointmentsPage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{appointment-id}")
    public ResponseEntity<ResponseBody> getAppointment(@PathVariable("appointment-id") UUID appointmentId,
                                                       JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Appointment appointment = vetAppointmentService.getDetailedAppointment(vetId, appointmentId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetAppointmentMapper.map(appointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/{appointment-id}/conclusion")
    public ResponseEntity<ResponseBody> updateAppointmentConclusion(@PathVariable("appointment-id") UUID appointmentId,
                                                                    @RequestBody @Valid UpdateAppointmentConclusionRequest updateAppointmentConclusionRequest,
                                                                    JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Appointment appointment = vetAppointmentService.updateAppointmentConclusion(vetId, appointmentId, updateAppointmentConclusionRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetAppointmentMapper.map(appointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/{appointment-id}/cancel")
    public ResponseEntity<ResponseBody> cancelAppointment(@PathVariable("appointment-id") UUID appointmentId,
                                                          JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        vetAppointmentService.cancelAppointment(vetId, appointmentId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/{appointment-id}/complete")
    public ResponseEntity<ResponseBody> completeAppointment(@PathVariable("appointment-id") UUID appointmentId,
                                                            JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        vetAppointmentService.completeAppointment(vetId, appointmentId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
