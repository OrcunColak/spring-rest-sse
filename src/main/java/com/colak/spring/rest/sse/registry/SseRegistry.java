package com.colak.spring.rest.sse.registry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SseRegistry {
    private final Map<SseClientId, SseConnection> clients = new ConcurrentHashMap<>();

    public void add(SseConnection connection) {
        SseClientId id = connection.id();
        clients.put(id, connection);
        log.info("SSE client connected: {} | totalClients: {}", id, clients.size());

        connection.onCompletion(() -> remove(id, "completion"));

        connection.onTimeout(() -> remove(id, "timeout"));

        connection.onError(e -> {
            log.warn("SSE client error: {} | message={}", id, e.getMessage(), e);
            remove(id, "error");
        });
    }

    public boolean disconnect(SseClientId clientId) {
        SseConnection sseConnection = clients.remove(clientId);
        if (sseConnection == null) {
            return false;
        }
        sseConnection.complete();
        return true;
    }

    private void remove(SseClientId clientId, String reason) {
        boolean removed = clients.remove(clientId) != null;
        if (removed) {
            log.info("Sse client removed: {} | reason={} totalClients:{}",
                    clientId, reason, clients.size());
        }
    }

    public List<SseConnection> findByUser(String userName) {
        return clients.values().stream()
                .filter(c -> c.id().userId().equals(userName))
                .toList();
    }

    public Collection<SseConnection> all() {
        return clients.values();
    }
}
