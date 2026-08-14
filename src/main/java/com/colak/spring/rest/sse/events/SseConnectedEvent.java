package com.colak.spring.rest.sse.events;


import com.colak.spring.rest.sse.protocol.SseEventType;
import com.colak.spring.rest.sse.protocol.SseProtocolVersion;

import java.time.Instant;

public record SseConnectedEvent(
        String userId,
        String connectionId,
        Instant connectedAt,
        SseEventType eventType,
        SseProtocolVersion protocolVersion
) {

    public static SseConnectedEvent of(String userId, String connectionId) {
        return new SseConnectedEvent(
                userId,
                connectionId,
                Instant.now(),
                SseEventType.CONNECTED,
                SseProtocolVersion.V1
        );
    }
}
