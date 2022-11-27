package com.helpet.appointmentservice.service;

import com.helpet.appointmentservice.service.error.FailedToGetAppointmentLocalizedError;
import com.helpet.appointmentservice.service.error.FailedToGetAppointmentsLocalizedError;
import com.helpet.appointmentservice.store.model.Appointment;
import com.helpet.appointmentservice.store.repository.AppointmentRepository;
import com.helpet.exception.NotFoundLocalizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    private final VetService vetService;

    private final UserService userService;

    private final FamilyService familyService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, VetService vetService, UserService userService, FamilyService familyService) {
        this.appointmentRepository = appointmentRepository;
        this.vetService = vetService;
        this.userService = userService;
        this.familyService = familyService;
    }

    public Page<Appointment> getVetAppointments(UUID vetId, Pageable pageable) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(FailedToGetAppointmentsLocalizedError.VET_DOES_NOT_EXIST);
        }

        return appointmentRepository.findAllByVetId(vetId, pageable);
    }

    public Appointment getVetAppointment(UUID vetId, UUID appointmentId) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(FailedToGetAppointmentLocalizedError.VET_DOES_NOT_EXIST);
        }

        Optional<Appointment> appointment = appointmentRepository.findByVetIdAndId(vetId, appointmentId);
        if (appointment.isEmpty()) {
            throw new NotFoundLocalizedException(FailedToGetAppointmentLocalizedError.VET_DOES_NOT_HAVE_THIS_APPOINTMENT);
        }

        return appointment.get();
    }

    /*public Page<Appointment> getVetFutureAppointments(UUID vetId, Pageable pageable) {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(FailedToGetAppointmentLocalizedError.VET_DOES_NOT_EXIST);
        }

        return appointmentRepository.findAllByVetIdAndScheduledAtIsAfter(vetId, OffsetDateTime.now(), pageable);
    }*/

    public Page<Appointment> getPetAppointments(UUID clientId,
                                                UUID familyId,
                                                UUID petId,
                                                Pageable pageable) throws NotFoundLocalizedException {
        if (!userService.userExists(clientId)) {
            throw new NotFoundLocalizedException(FailedToGetAppointmentsLocalizedError.USER_DOES_NOT_EXIST);
        }

        if (!familyService.familyHasMember(familyId, clientId)) {
            throw new NotFoundLocalizedException(FailedToGetAppointmentsLocalizedError.USER_IS_NOT_FAMILY_MEMBER);
        }

        if (!familyService.familyHasPet(familyId, petId)) {
            throw new NotFoundLocalizedException(FailedToGetAppointmentsLocalizedError.PET_IS_NOT_FAMILY_MEMBER);
        }

        return appointmentRepository.findAllByPetId(petId, pageable);
    }

    public Appointment getPetAppointment(UUID clientId,
                                         UUID familyId,
                                         UUID petId,
                                         UUID appointmentId) throws NotFoundLocalizedException {
        if (!userService.userExists(clientId)) {
            throw new NotFoundLocalizedException(FailedToGetAppointmentLocalizedError.USER_DOES_NOT_EXIST);
        }

        if (!familyService.familyHasMember(familyId, clientId)) {
            throw new NotFoundLocalizedException(FailedToGetAppointmentLocalizedError.USER_IS_NOT_FAMILY_MEMBER);
        }

        if (!familyService.familyHasPet(familyId, petId)) {
            throw new NotFoundLocalizedException(FailedToGetAppointmentLocalizedError.PET_IS_NOT_FAMILY_MEMBER);
        }

        Optional<Appointment> appointment = appointmentRepository.findByPetIdAndId(petId, appointmentId);
        if (appointment.isEmpty()) {
            throw new NotFoundLocalizedException(FailedToGetAppointmentLocalizedError.PET_DOES_NOT_HAVE_THIS_APPOINTMENT);
        }

        return appointment.get();
    }
}
