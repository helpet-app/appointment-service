package com.helpet.service.appointment.service;

import com.helpet.service.appointment.service.error.NotFoundLocalizedError;
import com.helpet.service.appointment.storage.model.Vet;
import com.helpet.service.appointment.storage.repository.VetRepository;
import com.helpet.exception.NotFoundLocalizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VetService {
    private final VetRepository vetRepository;

    @Autowired
    public VetService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    public boolean vetExists(UUID vetId) {
        return vetRepository.existsById(vetId);
    }

    public Vet getVet(UUID vetId) throws NotFoundLocalizedException {
        return vetRepository.findVetById(vetId)
                            .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST));
    }
}
