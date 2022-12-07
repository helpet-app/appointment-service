package com.helpet.service.appointment.service;

import com.helpet.service.appointment.service.error.ForbiddenLocalizedError;
import com.helpet.service.appointment.service.error.NotFoundLocalizedError;
import com.helpet.service.appointment.store.model.Appointment;
import com.helpet.service.appointment.store.repository.AppointmentRepository;
import com.helpet.exception.ForbiddenLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
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

        return appointmentRepository.findAllByPetId(petId, pageable);
    }

    public Appointment getAppointment(UUID petId, UUID appointmentId) throws NotFoundLocalizedException {
        if (!petService.petExists(petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_EXIST);
        }

        return appointmentRepository.findByPetIdAndId(petId, appointmentId)
                                    .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_HAVE_THIS_APPOINTMENT));
    }

    public Page<Appointment> getAppointments(UUID userId,
                                             UUID petId,
                                             Pageable pageable) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        if (!petService.userIsRelatedToPet(userId, petId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_RELATED_TO_PET);
        }

        return appointmentRepository.findAllByPetId(petId, pageable);
    }

    public Appointment getAppointment(UUID userId,
                                      UUID petId,
                                      UUID appointmentId) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        if (!petService.userIsRelatedToPet(userId, petId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_RELATED_TO_PET);
        }

        return appointmentRepository.findByPetIdAndId(petId, appointmentId)
                                    .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_HAVE_THIS_APPOINTMENT));
    }
}
