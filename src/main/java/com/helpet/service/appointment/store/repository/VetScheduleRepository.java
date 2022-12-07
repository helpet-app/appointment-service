package com.helpet.service.appointment.store.repository;

import com.helpet.service.appointment.store.model.VetScheduleSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VetScheduleRepository extends JpaRepository<VetScheduleSlot, UUID> {
    List<VetScheduleSlot> findAllByVetId(UUID vetId);
}
