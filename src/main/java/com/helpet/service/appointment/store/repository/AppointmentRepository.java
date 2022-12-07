package com.helpet.service.appointment.store.repository;

import com.helpet.service.appointment.store.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    Page<Appointment> findAllByVetId(UUID vetId, Pageable pageable);

    Optional<Appointment> findByVetIdAndId(UUID vetId, UUID id);

    Page<Appointment> findAllByClientId(UUID clientId, Pageable pageable);

    Optional<Appointment> findByClientIdAndId(UUID clientId, UUID id);

    Page<Appointment> findAllByPetId(UUID petId, Pageable pageable);

    Optional<Appointment> findByPetIdAndId(UUID petId, UUID id);
}
