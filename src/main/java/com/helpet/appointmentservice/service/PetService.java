package com.helpet.appointmentservice.service;

import com.helpet.appointmentservice.service.error.NotFoundLocalizedError;
import com.helpet.appointmentservice.store.model.Family;
import com.helpet.appointmentservice.store.model.Pet;
import com.helpet.appointmentservice.store.repository.PetRepository;
import com.helpet.exception.NotFoundLocalizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class PetService {
    private final FamilyService familyService;

    private final UserService userService;

    private final PetRepository petRepository;

    @Autowired
    public PetService(FamilyService familyService, UserService userService, PetRepository petRepository) {
        this.familyService = familyService;
        this.userService = userService;
        this.petRepository = petRepository;
    }

    public boolean petExists(UUID petId) {
        return petRepository.existsById(petId);
    }

    public Pet getPet(UUID petId) throws NotFoundLocalizedException {
        return petRepository.findPetById(petId).orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_EXIST));
    }

    public boolean userIsRelatedToPet(UUID userId, UUID petId) throws NotFoundLocalizedException {
        if (!userService.userExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_EXIST);
        }

        Pet pet = getPet(petId);

        Family family = pet.getFamily();
        if (Objects.isNull(family)) {
            return Objects.equals(pet.getOwner().getId(), userId);
        }

        return familyService.familyHasMember(family.getId(), userId);
    }
}
