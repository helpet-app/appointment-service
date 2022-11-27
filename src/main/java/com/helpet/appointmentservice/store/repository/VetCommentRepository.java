package com.helpet.appointmentservice.store.repository;

import com.helpet.appointmentservice.store.model.VetComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VetCommentRepository extends JpaRepository<VetComment, UUID> {
    Page<VetComment> findAllByAppointmentId(UUID appointmentId, Pageable pageable);
}
