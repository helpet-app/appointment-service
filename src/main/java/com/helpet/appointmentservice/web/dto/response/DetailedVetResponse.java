package com.helpet.appointmentservice.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DetailedVetResponse {
    private final UUID id;

    private final String firstName;

    private final String middleName;

    private final String lastName;

    private final String email;

    private final String avatarUrl;
}