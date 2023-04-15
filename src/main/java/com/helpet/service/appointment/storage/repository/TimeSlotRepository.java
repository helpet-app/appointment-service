package com.helpet.service.appointment.storage.repository;

import com.helpet.service.appointment.storage.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    Optional<TimeSlot> findTimeSlotById(int id);
}
