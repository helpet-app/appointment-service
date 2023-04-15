package com.helpet.service.appointment.service;

import com.helpet.service.appointment.service.error.NotFoundLocalizedError;
import com.helpet.service.appointment.storage.model.Account;
import com.helpet.service.appointment.storage.model.Pet;
import com.helpet.service.appointment.storage.repository.PetRepository;
import com.helpet.exception.NotFoundLocalizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PetService {
    private final AccountService accountService;

    private final PetRepository petRepository;

    @Autowired
    public PetService(AccountService accountService, PetRepository petRepository) {
        this.accountService = accountService;
        this.petRepository = petRepository;
    }

    public boolean petExists(UUID petId) {
        return petRepository.existsById(petId);
    }

    public Pet getPet(UUID petId) throws NotFoundLocalizedException {
        return petRepository.findPetById(petId).orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_EXIST));
    }

    public boolean userIsAssociatedWithPet(UUID userId, UUID petId) throws NotFoundLocalizedException {
        if (!accountService.accountExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST);
        }

        if (!petExists(petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_EXIST);
        }

        return petRepository.petIsAssociatedWithUser(petId, userId);
    }

    public boolean userIsAssociatedWithPet(Account user, Pet pet) {
        return petRepository.petIsAssociatedWithUser(pet.getId(), user.getId());
    }
}
