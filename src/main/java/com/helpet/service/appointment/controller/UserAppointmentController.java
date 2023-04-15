package com.helpet.service.appointment.controller;

import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.appointment.dto.request.CreateAppointmentRequest;
import com.helpet.service.appointment.dto.request.UpdateAppointmentRequest;
import com.helpet.service.appointment.mapper.UserAppointmentMapper;
import com.helpet.service.appointment.service.UserAppointmentService;
import com.helpet.service.appointment.storage.model.Appointment;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/user/appointments")
@RestController
public class UserAppointmentController {
    private final UserAppointmentService userAppointmentService;

    private final UserAppointmentMapper userAppointmentMapper;

    @Autowired
    public UserAppointmentController(UserAppointmentService userAppointmentService, UserAppointmentMapper userAppointmentMapper) {
        this.userAppointmentService = userAppointmentService;
        this.userAppointmentMapper = userAppointmentMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getAppointments(Pageable pageable, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Page<Appointment> appointmentsPage = userAppointmentService.getAppointments(userId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(userAppointmentMapper.mapPage(appointmentsPage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{appointment-id}")
    public ResponseEntity<ResponseBody> getAppointment(@PathVariable("appointment-id") UUID appointmentId,
                                                       JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Appointment appointment = userAppointmentService.getDetailedAppointment(userId, appointmentId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(userAppointmentMapper.map(appointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createAppointment(@RequestBody @Valid CreateAppointmentRequest createAppointmentRequest,
                                                          JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Appointment newAppointment = userAppointmentService.createAppointment(userId, createAppointmentRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(userAppointmentMapper.map(newAppointment));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PatchMapping("/{appointment-id}")
    public ResponseEntity<ResponseBody> updateAppointment(@PathVariable("appointment-id") UUID appointmentId,
                                                          @RequestBody @Valid UpdateAppointmentRequest updateAppointmentRequest,
                                                          JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Appointment newAppointment = userAppointmentService.updateAppointment(userId, appointmentId, updateAppointmentRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(userAppointmentMapper.map(newAppointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/{appointment-id}/cancel")
    public ResponseEntity<ResponseBody> cancelAppointment(@PathVariable("appointment-id") UUID appointmentId,
                                                          JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        userAppointmentService.cancelAppointment(userId, appointmentId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/{appointment-id}/complete")
    public ResponseEntity<ResponseBody> completeAppointment(@PathVariable("appointment-id") UUID appointmentId,
                                                            JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        userAppointmentService.completeAppointment(userId, appointmentId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
