package com.helpet.service.appointment.service;

import com.helpet.exception.BadRequestLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.appointment.dto.request.UpdateAppointmentConclusionRequest;
import com.helpet.service.appointment.service.error.NotFoundLocalizedError;
import com.helpet.service.appointment.storage.model.Appointment;
import com.helpet.service.appointment.storage.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VetAppointmentService {
    private final VetService vetService;

    private final CommonAppointmentService commonAppointmentService;

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public VetAppointmentService(VetService vetService,
                                 CommonAppointmentService commonAppointmentService,
                                 AppointmentRepository appointmentRepository) {
        this.vetService = vetService;
        this.commonAppointmentService = commonAppointmentService;
        this.appointmentRepository = appointmentRepository;
    }

    public Page<Appointment> getAppointments(UUID vetId, Pageable pageable) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        return appointmentRepository.findAllByVetIdOrderByScheduledAtDesc(vetId, pageable);
    }

    public Appointment getAppointment(UUID vetId, UUID appointmentId) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        return appointmentRepository.findAppointmentByVetIdAndId(vetId, appointmentId)
                                    .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_HAVE_THIS_APPOINTMENT));
    }

    public Appointment getDetailedAppointment(UUID vetId, UUID appointmentId) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        return appointmentRepository.findDetailedAppointmentByVetIdAndId(vetId, appointmentId)
                                    .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_HAVE_THIS_APPOINTMENT));
    }

    public Appointment updateAppointmentConclusion(UUID vetId,
                                                   UUID appointmentId,
                                                   UpdateAppointmentConclusionRequest appointmentConclusionInfo) throws NotFoundLocalizedException {
        Appointment appointment = getDetailedAppointment(vetId, appointmentId);

        appointment.setDiagnosis(appointmentConclusionInfo.getDiagnosis());
        appointment.setRecommendations(appointmentConclusionInfo.getRecommendations());

        return appointmentRepository.save(appointment);
    }

    public void cancelAppointment(UUID vetId, UUID appointmentId) throws NotFoundLocalizedException, BadRequestLocalizedException {
        Appointment appointment = getAppointment(vetId, appointmentId);

        commonAppointmentService.cancelAppointment(appointment);
    }

    public void completeAppointment(UUID vetId, UUID appointmentId) throws NotFoundLocalizedException, BadRequestLocalizedException {
        Appointment appointment = getAppointment(vetId, appointmentId);

        commonAppointmentService.completeAppointment(appointment);
    }
}
