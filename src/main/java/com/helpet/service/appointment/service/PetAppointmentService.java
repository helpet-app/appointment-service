package com.helpet.service.appointment.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.appointment.service.error.NotFoundLocalizedError;
import com.helpet.service.appointment.storage.model.Appointment;
import com.helpet.service.appointment.storage.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PetAppointmentService {
    private final PetService petService;

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public PetAppointmentService(PetService petService, AppointmentRepository appointmentRepository) {
        this.petService = petService;
        this.appointmentRepository = appointmentRepository;
    }

    public Page<Appointment> getAppointments(UUID petId, Pageable pageable) throws NotFoundLocalizedException {
        if (!petService.petExists(petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_EXIST);
        }

        return appointmentRepository.findAllByPetIdOrderByScheduledAtDesc(petId, pageable);
    }

    public Appointment getAppointment(UUID petId, UUID appointmentId) throws NotFoundLocalizedException {
        if (!petService.petExists(petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_EXIST);
        }

        return appointmentRepository.findDetailedAppointmentByPetIdAndId(petId, appointmentId)
                                    .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_HAVE_THIS_APPOINTMENT));
    }

    public Page<Appointment> getAppointments(UUID userId,
                                             UUID petId,
                                             Pageable pageable) throws NotFoundLocalizedException {
        if (!petService.userIsAssociatedWithPet(userId, petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_PET);
        }

        return getAppointments(petId, pageable);
    }

    public Appointment getAppointment(UUID userId,
                                      UUID petId,
                                      UUID appointmentId) throws NotFoundLocalizedException {
        if (!petService.userIsAssociatedWithPet(userId, petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_PET);
        }

        return getAppointment(petId, appointmentId);
    }
}
