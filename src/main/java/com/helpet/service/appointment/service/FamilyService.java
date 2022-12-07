package com.helpet.service.appointment.service;

import com.helpet.service.appointment.store.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FamilyService {
    private final FamilyRepository familyRepository;

    @Autowired
    public FamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    public boolean familyHasMember(UUID familyId, UUID memberId) {
        return familyRepository.existsByIdAndMembersId(familyId, memberId);
    }
}
