package com.helpet.service.appointment.store.repository;

import com.helpet.service.appointment.store.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VetRepository extends JpaRepository<Vet, UUID> {
    Optional<Vet> findVetById(UUID id);
}
