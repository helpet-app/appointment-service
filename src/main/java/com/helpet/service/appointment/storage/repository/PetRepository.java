package com.helpet.service.appointment.storage.repository;

import com.helpet.service.appointment.storage.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {
    Optional<Pet> findPetById(UUID petId);

    @Query(value = "SELECT * FROM pet_is_associated_with_user(:petId, :userId)", nativeQuery = true)
    boolean petIsAssociatedWithUser(UUID petId, UUID userId);
}
