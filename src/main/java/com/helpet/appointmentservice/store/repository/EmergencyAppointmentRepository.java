package com.helpet.appointmentservice.store.repository;

import com.helpet.appointmentservice.store.model.EmergencyAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmergencyAppointmentRepository extends JpaRepository<EmergencyAppointment, UUID> {
}
