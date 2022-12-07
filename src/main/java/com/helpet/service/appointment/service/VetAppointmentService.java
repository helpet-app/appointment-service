package com.helpet.service.appointment.service;

import com.helpet.service.appointment.service.error.NotFoundLocalizedError;
import com.helpet.service.appointment.store.model.Appointment;
import com.helpet.service.appointment.store.repository.AppointmentRepository;
import com.helpet.service.appointment.web.dto.request.UpdateAppointmentDiagnosisRequest;
import com.helpet.service.appointment.web.dto.request.UpdateAppointmentRecommendationsRequest;
import com.helpet.service.appointment.web.dto.request.UpdateAppointmentStatusRequest;
import com.helpet.exception.NotFoundLocalizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VetAppointmentService {
    private final VetService vetService;

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public VetAppointmentService(VetService vetService, AppointmentRepository appointmentRepository) {
        this.vetService = vetService;
        this.appointmentRepository = appointmentRepository;
    }

    public Page<Appointment> getAppointments(UUID vetId, Pageable pageable) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        return appointmentRepository.findAllByVetId(vetId, pageable);
    }

    public Appointment getAppointment(UUID vetId, UUID appointmentId) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        return appointmentRepository.findByVetIdAndId(vetId, appointmentId)
                                    .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_HAVE_THIS_APPOINTMENT));
    }

    public Appointment updateAppointmentDiagnosis(UUID vetId,
                                                  UUID appointmentId,
                                                  UpdateAppointmentDiagnosisRequest updateAppointmentDiagnosisRequest) throws NotFoundLocalizedException {
        Appointment appointment = getAppointment(vetId, appointmentId);
        appointment.setDiagnosis(updateAppointmentDiagnosisRequest.getDiagnosis());
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointmentRecommendations(UUID vetId,
                                                        UUID appointmentId,
                                                        UpdateAppointmentRecommendationsRequest updateAppointmentRecommendationsRequest) throws NotFoundLocalizedException {
        Appointment appointment = getAppointment(vetId, appointmentId);
        appointment.setRecommendations(updateAppointmentRecommendationsRequest.getRecommendations());
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointmentStatus(UUID vetId,
                                               UUID appointmentId,
                                               UpdateAppointmentStatusRequest updateAppointmentStatusRequest) throws NotFoundLocalizedException {
        Appointment appointment = getAppointment(vetId, appointmentId);
        appointment.setStatus(updateAppointmentStatusRequest.getStatus());
        return appointmentRepository.save(appointment);
    }
}
