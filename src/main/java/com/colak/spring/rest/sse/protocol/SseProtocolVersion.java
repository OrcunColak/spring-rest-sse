package com.colak.spring.rest.sse.protocol;

public enum SseProtocolVersion {
    V1("1.0");

    private final String value;

    SseProtocolVersion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
