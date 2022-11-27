package com.helpet.appointmentservice.store.repository;

import com.helpet.appointmentservice.store.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    Page<Appointment> findAllByVetId(UUID vetId, Pageable pageable);

    Optional<Appointment> findByVetIdAndId(UUID vetId, UUID id);

    Page<Appointment> findAllByVetIdAndScheduledAtIsAfter(UUID vetId, OffsetDateTime time, Pageable pageable);

    Page<Appointment> findAllByClientId(UUID clientId, Pageable pageable);

    Optional<Appointment> findByClientIdAndId(UUID clientId, UUID id);

    Page<Appointment> findAllByPetId(UUID petId, Pageable pageable);

    Optional<Appointment> findByPetIdAndId(UUID petId, UUID id);
}
