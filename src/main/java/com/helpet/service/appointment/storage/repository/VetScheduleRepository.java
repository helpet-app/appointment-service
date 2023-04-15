package com.helpet.service.appointment.storage.repository;

import com.helpet.service.appointment.storage.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface VetScheduleRepository extends JpaRepository<TimeSlot, UUID> {
    @Query(value = "SELECT * FROM find_free_vet_time_slots_by_date(:vetId, :date)", nativeQuery = true)
    List<TimeSlot> findFreeVetTimeSlotsByDateOrderByStartTime(UUID vetId, LocalDate date);
}
