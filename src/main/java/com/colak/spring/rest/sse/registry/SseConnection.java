package com.colak.spring.rest.sse.registry;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class SseConnection {
    private final SseClientId clientId;
    private final SseEmitter emitter;

    public SseClientId id() {
        return clientId;
    }

    public void onCompletion(Runnable callback) {
        emitter.onCompletion(callback);
    }

    public void onTimeout(Runnable callback) {
        emitter.onTimeout(callback);
    }

    public void onError(Consumer<Throwable> callback) {
        emitter.onError(callback);
    }

    public void send(SseEmitter.SseEventBuilder builder) throws IOException {
        emitter.send(builder);
    }

    public void send(Object object) throws IOException {
        emitter.send(object);
    }

    public void complete() {
        emitter.complete();
    }

    public void completeWithError(Exception e) {
        emitter.completeWithError(e);
    }
}
