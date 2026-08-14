package com.colak.spring.rest.sse.heartbeat;

import com.colak.spring.rest.sse.events.SseHeartBeatEvent;
import com.colak.spring.rest.sse.publisher.SseEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class SseHeartBeatService {
    private final SseEventService sseEventService;

    // every 25 seconds (safe for proxies)
    @Scheduled(fixedRate = 25_000)
    public void sendHeartBeat() {
        sendHeartBeatWithData();
    }

    // Ultra light heart beat : No JSON overhead, comment only
    private void sendCommentOnlyHeartBeat() {
        var builder = SseEmitter.event().comment("heartbeat");
        sseEventService.sendToAll(builder);
    }

    private void sendHeartBeatWithData() {
        SseHeartBeatEvent heartBeatEvent = SseHeartBeatEvent.now();
        var builder = SseEmitter.event()
                .name(heartBeatEvent.eventType().name())
                .data(heartBeatEvent);
        sseEventService.sendToAll(builder);
    }
}
