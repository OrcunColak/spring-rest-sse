package com.colak.spring.rest.sse;

import com.colak.spring.rest.sse.dto.request.DisconnectRequest;
import com.colak.spring.rest.sse.registry.SseClientId;
import com.colak.spring.rest.sse.registry.SseConnection;
import com.colak.spring.rest.sse.registry.SseRegistry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

/// Accepting userId directly from the request is only appropriate if the endpoint is otherwise trusted.
/// If the endpoint is publicly accessible, a client could simply provide another user's ID and potentially receive their SSE events.
/// So we are using Spring Security
@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
public class CommandSseController {
    private final SseRegistry registry;

    /// Creates the SseEmitter, builds the SseClientId from Spring Security's Authentication, registers the connection,
    /// and returns the emitter
    @GetMapping("/connect")
    public SseEmitter connect(Authentication authentication) {
        String userName = authentication.getName();

        SseEmitter emitter = new SseEmitter();
        SseClientId clientId = new SseClientId(userName, UUID.randomUUID().toString());

        SseConnection connection = new SseConnection(clientId, emitter);
        registry.add(connection);

        return emitter;
    }

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
