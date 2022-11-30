package com.helpet.appointmentservice.web.controller;

import com.helpet.appointmentservice.service.UserAppointmentService;
import com.helpet.appointmentservice.store.model.Appointment;
import com.helpet.appointmentservice.web.dto.request.CreateAppointmentRequest;
import com.helpet.appointmentservice.web.dto.request.UpdateAppointmentStatusRequest;
import com.helpet.appointmentservice.web.mapper.AppointmentMapper;
import com.helpet.appointmentservice.web.mapper.DetailedAppointmentMapper;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RolesAllowed(Role.USER)
@RequestMapping("/user/appointments")
@RestController
public class UserAppointmentController {
    private final UserAppointmentService userAppointmentService;

    private final AppointmentMapper appointmentMapper;

    private final DetailedAppointmentMapper detailedAppointmentMapper;

    @Autowired
    public UserAppointmentController(UserAppointmentService userAppointmentService,
                                     AppointmentMapper appointmentMapper,
                                     DetailedAppointmentMapper detailedAppointmentMapper) {
        this.userAppointmentService = userAppointmentService;
        this.appointmentMapper = appointmentMapper;
        this.detailedAppointmentMapper = detailedAppointmentMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getAppointments(Pageable pageable, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Page<Appointment> appointmentsPage = userAppointmentService.getAppointments(userId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(appointmentMapper.mapPage(appointmentsPage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{appointment-id}")
    public ResponseEntity<ResponseBody> getAppointment(@PathVariable("appointment-id") UUID appointmentId,
                                                       JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Appointment appointment = userAppointmentService.getAppointment(userId, appointmentId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(detailedAppointmentMapper.map(appointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createAppointment(@RequestBody CreateAppointmentRequest createAppointmentRequest,
                                                          JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Appointment newAppointment = userAppointmentService.createAppointment(userId, createAppointmentRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(detailedAppointmentMapper.map(newAppointment));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PatchMapping("/{appointment-id}/status")
    public ResponseEntity<ResponseBody> updateDiagnosis(@PathVariable("appointment-id") UUID appointmentId,
                                                        @RequestBody UpdateAppointmentStatusRequest updateAppointmentStatusRequest,
                                                        JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Appointment appointment = userAppointmentService.updateAppointmentStatus(vetId, appointmentId, updateAppointmentStatusRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(detailedAppointmentMapper.map(appointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
