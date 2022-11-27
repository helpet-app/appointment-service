package com.helpet.appointmentservice.service;

import com.helpet.appointmentservice.service.error.FailedToGetFreeTimeSlotsLocalizedError;
import com.helpet.appointmentservice.service.error.FailedToGetScheduleLocalizedError;
import com.helpet.appointmentservice.store.model.TimeSlot;
import com.helpet.appointmentservice.store.model.VetSchedule;
import com.helpet.appointmentservice.store.repository.TimeSlotRepository;
import com.helpet.appointmentservice.store.repository.VetRepository;
import com.helpet.appointmentservice.store.repository.VetScheduleRepository;
import com.helpet.exception.NotFoundLocalizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class VetService {
    private final VetRepository vetRepository;

    private final VetScheduleRepository vetScheduleRepository;

    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public VetService(VetRepository vetRepository, VetScheduleRepository vetScheduleRepository, TimeSlotRepository timeSlotRepository) {
        this.vetRepository = vetRepository;
        this.vetScheduleRepository = vetScheduleRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public boolean vetExists(UUID vetId) {
        return vetRepository.existsById(vetId);
    }

    public List<VetSchedule> getSchedule(UUID vetId) throws NotFoundLocalizedException {
        if (!vetExists(vetId)) {
            throw new NotFoundLocalizedException(FailedToGetScheduleLocalizedError.VET_DOES_NOT_EXIST);
        }

        return vetScheduleRepository.findAllByVetId(vetId);
    }

    public List<TimeSlot> getFreeTimeSlotsByDate(UUID vetId, LocalDate date) throws NotFoundLocalizedException {
        if (!vetExists(vetId)) {
            throw new NotFoundLocalizedException(FailedToGetFreeTimeSlotsLocalizedError.VET_DOES_NOT_EXIST);
        }

        return timeSlotRepository.findFreeVetTimeSlotsByDate(vetId, date);
    }
}
