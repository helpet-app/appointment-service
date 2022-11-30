package com.helpet.appointmentservice.web.controller;

import com.helpet.appointmentservice.service.VetAppointmentService;
import com.helpet.appointmentservice.store.model.Appointment;
import com.helpet.appointmentservice.web.dto.request.UpdateAppointmentDiagnosisRequest;
import com.helpet.appointmentservice.web.dto.request.UpdateAppointmentRecommendationsRequest;
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

@RolesAllowed(Role.VET)
@RequestMapping("/vet/appointments")
@RestController
public class VetAppointmentController {
    private final VetAppointmentService vetAppointmentService;

    private final AppointmentMapper appointmentMapper;

    private final DetailedAppointmentMapper detailedAppointmentMapper;

    @Autowired
    public VetAppointmentController(VetAppointmentService vetAppointmentService,
                                    AppointmentMapper appointmentMapper,
                                    DetailedAppointmentMapper detailedAppointmentMapper) {
        this.vetAppointmentService = vetAppointmentService;
        this.appointmentMapper = appointmentMapper;
        this.detailedAppointmentMapper = detailedAppointmentMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getAppointments(Pageable pageable, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Page<Appointment> appointmentsPage = vetAppointmentService.getAppointments(vetId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(appointmentMapper.mapPage(appointmentsPage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{appointment-id}")
    public ResponseEntity<ResponseBody> getAppointment(@PathVariable("appointment-id") UUID appointmentId,
                                                       JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Appointment appointment = vetAppointmentService.getAppointment(vetId, appointmentId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(detailedAppointmentMapper.map(appointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/{appointment-id}/diagnosis")
    public ResponseEntity<ResponseBody> updateDiagnosis(@PathVariable("appointment-id") UUID appointmentId,
                                                        @RequestBody UpdateAppointmentDiagnosisRequest updateAppointmentDiagnosisRequest,
                                                        JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Appointment appointment = vetAppointmentService.updateAppointmentDiagnosis(vetId, appointmentId, updateAppointmentDiagnosisRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(detailedAppointmentMapper.map(appointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/{appointment-id}/recommendations")
    public ResponseEntity<ResponseBody> updateDiagnosis(@PathVariable("appointment-id") UUID appointmentId,
                                                        @RequestBody UpdateAppointmentRecommendationsRequest updateAppointmentRecommendationsRequest,
                                                        JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Appointment appointment = vetAppointmentService.updateAppointmentRecommendations(vetId,
                                                                                         appointmentId,
                                                                                         updateAppointmentRecommendationsRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(detailedAppointmentMapper.map(appointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/{appointment-id}/status")
    public ResponseEntity<ResponseBody> updateDiagnosis(@PathVariable("appointment-id") UUID appointmentId,
                                                        @RequestBody UpdateAppointmentStatusRequest updateAppointmentStatusRequest,
                                                        JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Appointment appointment = vetAppointmentService.updateAppointmentStatus(vetId, appointmentId, updateAppointmentStatusRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(detailedAppointmentMapper.map(appointment));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
