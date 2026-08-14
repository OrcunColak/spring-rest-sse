package com.colak.spring.rest.sse;

import com.colak.spring.rest.sse.dto.request.DisconnectRequest;
import com.colak.spring.rest.sse.registry.SseClientId;
import com.colak.spring.rest.sse.registry.SseRegistry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
public class CommandSseController {
    private final SseRegistry registry;

    @DeleteMapping("/disconnect")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disconnect(@Valid @RequestBody DisconnectRequest request, Authentication authentication) {
        String userName = authentication.getName();

        SseClientId clientId = new SseClientId(userName, request.connectionId());
        boolean result = registry.disconnect(clientId);
        if (!result) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Connection not found");
        }
    }
}
