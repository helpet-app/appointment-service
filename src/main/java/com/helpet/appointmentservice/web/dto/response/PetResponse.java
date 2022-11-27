package com.helpet.appointmentservice.web.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class PetResponse {
    private UUID id;

    private String name;

    private String avatarUrl;
}