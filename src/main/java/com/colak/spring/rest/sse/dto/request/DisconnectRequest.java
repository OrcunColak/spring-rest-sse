package com.colak.spring.rest.sse.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DisconnectRequest(
        @NotNull
        @NotBlank
        String connectionId
) {
}
