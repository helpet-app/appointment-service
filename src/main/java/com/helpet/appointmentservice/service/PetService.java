package com.helpet.appointmentservice.service;

import com.helpet.appointmentservice.store.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PetService {
    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public boolean petExists(UUID petId) {
        return petRepository.existsById(petId);
    }
}
