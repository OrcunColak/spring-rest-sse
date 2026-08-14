package com.colak.spring.rest.sse.events;


import com.colak.spring.rest.sse.protocol.SseEventType;
import com.colak.spring.rest.sse.protocol.SseProtocolVersion;

import java.time.Instant;

public record SseHeartBeatEvent(
        Instant timestamp,
        SseEventType eventType,
        SseProtocolVersion protocolVersion
) {

    public static SseHeartBeatEvent now() {
        return new SseHeartBeatEvent(
                Instant.now(),
                SseEventType.HEARTBEAT,
                SseProtocolVersion.V1
        );
    }
}
