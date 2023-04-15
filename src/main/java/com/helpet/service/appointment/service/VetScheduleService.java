package com.helpet.service.appointment.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.appointment.service.error.NotFoundLocalizedError;
import com.helpet.service.appointment.storage.model.TimeSlot;
import com.helpet.service.appointment.storage.model.Vet;
import com.helpet.service.appointment.storage.repository.VetScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class VetScheduleService {
    private final VetService vetService;

    private final VetScheduleRepository vetScheduleRepository;

    @Autowired
    public VetScheduleService(VetService vetService, VetScheduleRepository vetScheduleRepository) {
        this.vetService = vetService;
        this.vetScheduleRepository = vetScheduleRepository;
    }

    public List<TimeSlot> getFreeTimeSlotsByDate(UUID vetId, LocalDate date) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        return vetScheduleRepository.findFreeVetTimeSlotsByDateOrderByStartTime(vetId, date);
    }

    public List<TimeSlot> getFreeTimeSlotsByDate(Vet vet, LocalDate date) throws NotFoundLocalizedException {
        return vetScheduleRepository.findFreeVetTimeSlotsByDateOrderByStartTime(vet.getId(), date);
    }
}
