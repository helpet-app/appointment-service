package com.helpet.appointmentservice.service;

import com.helpet.appointmentservice.service.error.NotFoundLocalizedError;
import com.helpet.appointmentservice.store.model.Vet;
import com.helpet.appointmentservice.store.repository.VetRepository;
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
