package com.colak.spring.rest.sse.events;


import com.colak.spring.rest.sse.protocol.SseEventType;
import com.colak.spring.rest.sse.protocol.SseProtocolVersion;

import java.time.Instant;

public record SseHeartBeatEvent(
        Instant timestamp,
        SseProtocolVersion protocolVersion
) {

    /// Send the event type
    public SseEventType eventType() {
        return SseEventType.HEARTBEAT;
    }

    public static SseHeartBeatEvent now() {
        return new SseHeartBeatEvent(
                Instant.now(),
                SseProtocolVersion.V1
        );
    }
}
