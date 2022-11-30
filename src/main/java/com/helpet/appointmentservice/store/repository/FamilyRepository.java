package com.helpet.appointmentservice.store.repository;

import com.helpet.appointmentservice.store.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID> {
    boolean existsByIdAndMembersId(UUID familyId, UUID memberId);
}
