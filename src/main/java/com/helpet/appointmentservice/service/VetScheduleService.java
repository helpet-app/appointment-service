package com.helpet.appointmentservice.service;

import com.helpet.appointmentservice.service.error.NotFoundLocalizedError;
import com.helpet.appointmentservice.store.model.TimeSlot;
import com.helpet.appointmentservice.store.model.VetScheduleSlot;
import com.helpet.appointmentservice.store.repository.TimeSlotRepository;
import com.helpet.appointmentservice.store.repository.VetScheduleRepository;
import com.helpet.exception.NotFoundLocalizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class VetScheduleService {
    private final VetService vetService;

    private final VetScheduleRepository vetScheduleRepository;

    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public VetScheduleService(VetService vetService, VetScheduleRepository vetScheduleRepository, TimeSlotRepository timeSlotRepository) {
        this.vetService = vetService;
        this.vetScheduleRepository = vetScheduleRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public List<VetScheduleSlot> getSchedule(UUID vetId) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        return vetScheduleRepository.findAllByVetId(vetId);
    }

    public List<TimeSlot> getFreeTimeSlotsByDate(UUID vetId, LocalDate date) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        return timeSlotRepository.findFreeVetTimeSlotsByDate(vetId, date);
    }
}
