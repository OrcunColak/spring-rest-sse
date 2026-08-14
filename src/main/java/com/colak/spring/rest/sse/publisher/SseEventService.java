package com.colak.spring.rest.sse.publisher;

import com.colak.spring.rest.sse.registry.SseConnection;
import com.colak.spring.rest.sse.registry.SseRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Slf4j
public class SseEventService {
    private final SseRegistry registry;

    public void sendToAll(SseEmitter.SseEventBuilder builder) {
        for (SseConnection connection : registry.all()) {
            try {
                connection.send(builder);
            } catch (Exception e) {
                log.debug("sendToAll failed for {}", connection.id());
            }
        }
    }

    public void sendToAll(Object object) {
        for (SseConnection connection : registry.all()) {
            try {
                connection.send(object);
            } catch (Exception e) {
                log.debug("sendToAll failed for {}", connection.id());
            }
        }
    }

    public void sendToUser(String userId,Object object) {
        for (SseConnection connection : registry.findByUser(userId)) {
            try {
                connection.send(object);
            } catch (Exception e) {
                log.debug("sendToUser failed for {}", connection.id());
            }
        }
    }
}
