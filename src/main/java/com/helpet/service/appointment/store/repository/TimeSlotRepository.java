package com.helpet.service.appointment.store.repository;

import com.helpet.service.appointment.store.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    @Query(value = "SELECT * FROM find_free_vet_time_slots_by_date(:vetId, :date)", nativeQuery = true)
    List<TimeSlot> findFreeVetTimeSlotsByDate(UUID vetId, LocalDate date);

    Optional<TimeSlot> findTimeSlotById(int id);
}
