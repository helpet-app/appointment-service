package com.helpet.service.appointment.storage.repository;

import com.helpet.service.appointment.storage.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    @EntityGraph(attributePaths = {
            "client",
            "pet"
    })
    Page<Appointment> findAllByVetIdOrderByScheduledAtDesc(UUID vetId, Pageable pageable);

    Optional<Appointment> findAppointmentByVetIdAndId(UUID vetId, UUID appointmentId);

    @EntityGraph(attributePaths = {
            "client",
            "pet"
    })
    Optional<Appointment> findDetailedAppointmentByVetIdAndId(UUID vetId, UUID appointmentId);

    @EntityGraph(attributePaths = {
            "vet",
            "pet"
    })
    Page<Appointment> findAllByClientIdOrderByScheduledAtDesc(UUID clientId, Pageable pageable);

    Optional<Appointment> findAppointmentByClientIdAndId(UUID clientId, UUID appointmentId);

    @EntityGraph(attributePaths = {
            "vet",
            "pet"
    })
    Optional<Appointment> findDetailedAppointmentByClientIdAndId(UUID clientId, UUID appointmentId);

    @EntityGraph(attributePaths = {
            "vet",
            "client"
    })
    Page<Appointment> findAllByPetIdOrderByScheduledAtDesc(UUID petId, Pageable pageable);

    @EntityGraph(attributePaths = {
            "vet",
            "client"
    })
    Optional<Appointment> findDetailedAppointmentByPetIdAndId(UUID petId, UUID appointmentId);
}
