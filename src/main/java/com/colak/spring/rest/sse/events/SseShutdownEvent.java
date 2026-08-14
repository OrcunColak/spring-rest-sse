package com.colak.spring.rest.sse.events;

import com.colak.spring.rest.sse.protocol.SseEventType;
import com.colak.spring.rest.sse.protocol.SseProtocolVersion;

public record SseShutdownEvent(
        String protocolVersion
) {
    public SseEventType eventType() {
        return SseEventType.SHUTDOWN;
    }

    public static SseShutdownEvent now() {
        return new SseShutdownEvent(
                SseProtocolVersion.V1.getValue()
        );
    }
}
