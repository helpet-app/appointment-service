package com.helpet.service.appointment.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserResponse {
    private final UUID id;

    private final String firstName;

    private final String middleName;

    private final String lastName;

    private final String avatarUrl;
}