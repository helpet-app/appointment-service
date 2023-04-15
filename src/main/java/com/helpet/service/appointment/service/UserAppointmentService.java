package com.helpet.service.appointment.service;

import com.helpet.exception.BadRequestLocalizedException;
import com.helpet.exception.ConflictLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.appointment.dto.request.CreateAppointmentRequest;
import com.helpet.service.appointment.dto.request.UpdateAppointmentRequest;
import com.helpet.service.appointment.service.error.BadRequestLocalizedError;
import com.helpet.service.appointment.service.error.ConflictLocalizedError;
import com.helpet.service.appointment.service.error.NotFoundLocalizedError;
import com.helpet.service.appointment.storage.model.*;
import com.helpet.service.appointment.storage.repository.AppointmentRepository;
import com.helpet.service.appointment.storage.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserAppointmentService {
    private final AccountService accountService;

    private final VetService vetService;

    private final PetService petService;

    private final VetScheduleService vetScheduleService;

    private final CommonAppointmentService commonAppointmentService;

    private final AppointmentRepository appointmentRepository;

    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public UserAppointmentService(AccountService accountService,
                                  VetService vetService,
                                  PetService petService,
                                  VetScheduleService vetScheduleService,
                                  CommonAppointmentService commonAppointmentService,
                                  AppointmentRepository appointmentRepository,
                                  TimeSlotRepository timeSlotRepository) {
        this.accountService = accountService;
        this.vetService = vetService;
        this.petService = petService;
        this.vetScheduleService = vetScheduleService;
        this.commonAppointmentService = commonAppointmentService;
        this.appointmentRepository = appointmentRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public Page<Appointment> getAppointments(UUID userId, Pageable pageable) throws NotFoundLocalizedException {
        if (!accountService.accountExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST);
        }

        return appointmentRepository.findAllByClientIdOrderByScheduledAtDesc(userId, pageable);
    }

    public Appointment getAppointment(UUID userId, UUID appointmentId) throws NotFoundLocalizedException {
        if (!accountService.accountExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST);
        }

        return appointmentRepository.findAppointmentByClientIdAndId(userId, appointmentId)
                                    .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_APPOINTMENT));
    }

    public Appointment getDetailedAppointment(UUID userId, UUID appointmentId) throws NotFoundLocalizedException {
        if (!accountService.accountExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST);
        }

        return appointmentRepository.findDetailedAppointmentByClientIdAndId(userId, appointmentId)
                                    .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_APPOINTMENT));
    }

    public Appointment createAppointment(UUID userId,
                                         CreateAppointmentRequest appointmentInfo) throws NotFoundLocalizedException, BadRequestLocalizedException, ConflictLocalizedException {
        Account client = accountService.getAccount(userId);

        Vet vet = vetService.getVet(appointmentInfo.getVetId());

        Pet pet = petService.getPet(appointmentInfo.getPetId());

        if (!petService.userIsAssociatedWithPet(client, pet)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_PET);
        }

        LocalDate date = appointmentInfo.getDate();
        if (date.isBefore(LocalDate.now().plusDays(1))) {
            throw new BadRequestLocalizedException(BadRequestLocalizedError.DATE_IS_INVALID);
        }

        TimeSlot timeSlot = timeSlotRepository.findTimeSlotById(appointmentInfo.getTimeSlotId())
                                              .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.TIME_SLOT_DOES_NOT_EXIST));

        List<TimeSlot> freeTimeSlots = vetScheduleService.getFreeTimeSlotsByDate(vet, appointmentInfo.getDate());
        if (!freeTimeSlots.contains(timeSlot)) {
            throw new ConflictLocalizedException(ConflictLocalizedError.TIME_SLOT_IS_NOT_AVAILABLE);
        }

        Appointment newAppointment = Appointment.builder()
                                                .problem(appointmentInfo.getProblem())
                                                .scheduledAt(date.atTime(timeSlot.getStartTime()))
                                                .vet(vet)
                                                .client(client)
                                                .pet(pet)
                                                .build();

        return appointmentRepository.save(newAppointment);
    }

    public Appointment updateAppointment(UUID userId, UUID appointmentId, UpdateAppointmentRequest appointmentInfo) {
        Appointment appointment = getDetailedAppointment(userId, appointmentId);

        appointment.setProblem(appointmentInfo.getProblem());

        return appointmentRepository.save(appointment);
    }

    public void cancelAppointment(UUID userId, UUID appointmentId) throws NotFoundLocalizedException, BadRequestLocalizedException {
        Appointment appointment = getAppointment(userId, appointmentId);

        commonAppointmentService.cancelAppointment(appointment);
    }

    public void completeAppointment(UUID userId, UUID appointmentId) throws NotFoundLocalizedException, BadRequestLocalizedException {
        Appointment appointment = getAppointment(userId, appointmentId);

        commonAppointmentService.completeAppointment(appointment);
    }
}
