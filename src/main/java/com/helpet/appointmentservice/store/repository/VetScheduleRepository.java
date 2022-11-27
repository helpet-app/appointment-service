package com.helpet.appointmentservice.store.repository;

import com.helpet.appointmentservice.store.model.VetSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VetScheduleRepository extends JpaRepository<VetSchedule, UUID> {
    List<VetSchedule> findAllByVetId(UUID vetId);
}
