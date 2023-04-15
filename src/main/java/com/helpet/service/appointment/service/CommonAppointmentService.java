package com.helpet.service.appointment.service;

import com.helpet.exception.BadRequestLocalizedException;
import com.helpet.service.appointment.service.error.BadRequestLocalizedError;
import com.helpet.service.appointment.storage.model.Appointment;
import com.helpet.service.appointment.storage.model.AppointmentStatus;
import com.helpet.service.appointment.storage.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class CommonAppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public CommonAppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void cancelAppointment(Appointment appointment) throws BadRequestLocalizedException {
        if (!appointment.getStatus().equals(AppointmentStatus.SCHEDULED)) {
            throw new BadRequestLocalizedException(BadRequestLocalizedError.CAN_ONLY_CANCEL_APPOINTMENT_WITH_SCHEDULED_STATUS);
        }

        if (OffsetDateTime.now().isAfter(appointment.getScheduledAt().minusDays(1))) {
            throw new BadRequestLocalizedException(BadRequestLocalizedError.IT_IS_TOO_LATE_TO_CANCEL_APPOINTMENT);
        }

        appointment.setStatus(AppointmentStatus.CANCELED);

        appointmentRepository.save(appointment);
    }

    public void completeAppointment(Appointment appointment) throws BadRequestLocalizedException {
        if (!appointment.getStatus().equals(AppointmentStatus.SCHEDULED)) {
            throw new BadRequestLocalizedException(BadRequestLocalizedError.CAN_ONLY_CANCEL_APPOINTMENT_WITH_SCHEDULED_STATUS);
        }

        if (OffsetDateTime.now().isBefore(appointment.getScheduledAt())) {
            throw new BadRequestLocalizedException(BadRequestLocalizedError.IT_IS_TOO_EARLY_TO_COMPLETE_APPOINTMENT);
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);

        appointmentRepository.save(appointment);
    }
}
