package com.colak.spring.rest.sse.registry;

public record SseClientId(
        String userId,
        String connectionId
) {

    @Override
    public String toString() {
        return userId + ":" + connectionId;
    }
}
