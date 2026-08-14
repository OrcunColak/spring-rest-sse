## Usage

Inject SseEventService into your service. Then call its API to send messages.

## SSE Event Service

A small Spring service for sending Server-Sent Events (SSE) to connected clients.

SseEventService uses an SseRegistry to keep track of active SSE connections and provides methods for broadcasting events
to all clients or sending an event to a specific user.

Features

- Send an event to all connected clients
- Send an object to all connected clients
- Send an object to all connections belonging to a specific user
- Automatically handles failed connections without interrupting other sends

## SSE Heartbeat Service

A small Spring service that periodically sends heartbeat events to all connected SSE clients.

The heartbeat helps keep SSE connections alive and reduces the risk of proxies, load balancers, or network
infrastructure closing an idle connection.

Features

- Sends a heartbeat every 25 seconds
- Broadcasts the heartbeat to all active SSE connections
- Uses a normal SSE event with event data
- Can optionally use a lightweight SSE comment instead of JSON data

## SSE Registry

SseRegistry manages all active Server-Sent Events (SSE) connections in the application.

It provides a central registry for registering, finding, and disconnecting SSE clients.

Features

- Register new SSE connections
- Automatically remove connections when they complete, timeout, or fail
- Find all connections belonging to a specific user
- Retrieve all active connections
- Programmatically disconnect a specific client
- Thread-safe connection management using ConcurrentHashMap

## SSE Connection

SseConnection is a small wrapper around Spring's SseEmitter.

It associates an SseEmitter with an SseClientId and provides a simple API for sending events and managing the connection
lifecycle. It also exposes the main SseEmitter lifecycle callbacks via onCompletion(), onTimeout(), onError() methods