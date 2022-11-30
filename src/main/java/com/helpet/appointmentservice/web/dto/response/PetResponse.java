package com.helpet.appointmentservice.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PetResponse {
    private final UUID id;

    private final String name;

    private final String avatarUrl;
}