package com.helpet.service.appointment.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class VetResponse {
    private UUID id;

    private String name;

    private String avatarUrl;
}