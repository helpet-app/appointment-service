package com.helpet.appointmentservice.web.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class UserCommentResponse {
    private UUID id;

    private String content;

    private OffsetDateTime publishedAt;

    private OffsetDateTime updatedAt;

    private UserSimplifiedResponse user;
}