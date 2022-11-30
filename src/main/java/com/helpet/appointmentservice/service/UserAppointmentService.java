package com.helpet.appointmentservice.service;

import com.helpet.appointmentservice.service.error.ConflictLocalizedError;
import com.helpet.appointmentservice.service.error.ForbiddenLocalizedError;
import com.helpet.appointmentservice.service.error.NotFoundLocalizedError;
import com.helpet.appointmentservice.store.model.*;
import com.helpet.appointmentservice.store.repository.AppointmentRepository;
import com.helpet.appointmentservice.store.repository.TimeSlotRepository;
import com.helpet.appointmentservice.web.dto.request.CreateAppointmentRequest;
import com.helpet.appointmentservice.web.dto.request.UpdateAppointmentStatusRequest;
import com.helpet.exception.ConflictLocalizedException;
import com.helpet.exception.ForbiddenLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserAppointmentService {
    private final UserService userService;

    private final VetService vetService;

    private final PetService petService;

    private final VetScheduleService vetScheduleService;

    private final AppointmentRepository appointmentRepository;

    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public UserAppointmentService(UserService userService,
                                  VetService vetService,
                                  PetService petService,
                                  VetScheduleService vetScheduleService,
                                  AppointmentRepository appointmentRepository,
                                  TimeSlotRepository timeSlotRepository) {
        this.userService = userService;
        this.vetService = vetService;
        this.petService = petService;
        this.vetScheduleService = vetScheduleService;
        this.appointmentRepository = appointmentRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public Page<Appointment> getAppointments(UUID userId, Pageable pageable) throws NotFoundLocalizedException {
        if (!userService.userExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_EXIST);
        }

        return appointmentRepository.findAllByClientId(userId, pageable);
    }

    public Appointment getAppointment(UUID userId, UUID appointmentId) throws NotFoundLocalizedException {
        if (!userService.userExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_EXIST);
        }

        return appointmentRepository.findByClientIdAndId(userId, appointmentId)
                                    .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_APPOINTMENT));
    }

    public Appointment createAppointment(UUID userId,
                                         CreateAppointmentRequest appointmentInfo) throws NotFoundLocalizedException, ForbiddenLocalizedException, ConflictLocalizedException {
        User client = userService.getUser(userId);

        Vet vet = vetService.getVet(appointmentInfo.getVetId());

        Pet pet = petService.getPet(appointmentInfo.getPetId());

        if (!petService.userIsRelatedToPet(userId, pet.getId())) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_RELATED_TO_PET);
        }

        LocalDate date = appointmentInfo.getDate();
        if (date.isBefore(LocalDate.now().plusDays(1))) {
            throw new ConflictLocalizedException(ConflictLocalizedError.DATE_IS_INVALID);
        }

        TimeSlot timeSlot = timeSlotRepository.findTimeSlotById(appointmentInfo.getTimeSlotId())
                                              .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.TIME_SLOT_DOES_NOT_EXIST));

        List<TimeSlot> freeTimeSlots = vetScheduleService.getFreeTimeSlotsByDate(vet.getId(), appointmentInfo.getDate());
        if (!freeTimeSlots.contains(timeSlot)) {
            throw new ConflictLocalizedException(ConflictLocalizedError.TIME_SLOT_IS_NOT_AVAILABLE);
        }

        Appointment newAppointment = new Appointment();

        newAppointment.setClient(client);
        newAppointment.setVet(vet);
        newAppointment.setPet(pet);
        newAppointment.setProblem(appointmentInfo.getProblem());
        newAppointment.setScheduledAt(date.atTime(timeSlot.getStartTime()));

        return appointmentRepository.save(newAppointment);
    }

    public Appointment updateAppointmentStatus(UUID userId,
                                               UUID appointmentId,
                                               UpdateAppointmentStatusRequest updateAppointmentStatusRequest) throws NotFoundLocalizedException {
        Appointment appointment = getAppointment(userId, appointmentId);
        appointment.setStatus(updateAppointmentStatusRequest.getStatus());
        return appointmentRepository.save(appointment);
    }
}
