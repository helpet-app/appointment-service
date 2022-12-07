package com.helpet.service.appointment.store.repository;

import com.helpet.service.appointment.store.model.EmergencyAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmergencyAppointmentRepository extends JpaRepository<EmergencyAppointment, UUID> {
}
