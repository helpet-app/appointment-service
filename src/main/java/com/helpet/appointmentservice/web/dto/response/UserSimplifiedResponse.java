package com.helpet.appointmentservice.web.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UserSimplifiedResponse {
    private UUID id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String avatarUrl;
}