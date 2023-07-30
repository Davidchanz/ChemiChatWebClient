package com.chemichat.web.service;

import com.chemichat.web.dto.MessageDto;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.apache.tomcat.util.net.SocketProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Service
public class StompService implements StompSessionHandler {
    StompSession stompSession = null;

    private final static String url = "http://localhost:8080/ws";

    /**
     * Map of subscriptions.
     */
    @Getter
    Map<String, StompSession.Subscription> subscriptions = new HashMap<>();

    /**
     * This connects to a stomp server on application startup, so that it can wait for channel entrance requests.
     */
    @EventListener(value = ApplicationReadyEvent.class)
    public void connect() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        try {
            stompSession = stompClient
                    .connectAsync(url, headers, this)
                    .get();
        } catch (Exception e) {
            System.out.println("Connection failed."); // TODO: Do some failover and implement retry patterns.
        }
    }

    public boolean isConnected() {
        return stompSession.isConnected();
    }

    /**
     * Subscribes to the feed of the room.
     *
     * @param roomId The id of the room to subscribe for.
     */
    public void subscribe(String roomId) {
        // TODO: Check subscriptions if already exist and is subscribed.
        System.out.println("Subscribing to room: {}"+ roomId);
        StompSession.Subscription subscription = stompSession.subscribe("/channel/" + roomId, this);
        subscriptions.put(roomId, subscription);
    }

    public boolean isSubscribed(String roomId) {
        return subscriptions
                .containsKey(roomId);
    }

    public void unsubscribe(String roomId) {
        subscriptions.get(roomId).unsubscribe();
        subscriptions.remove(roomId);
    }

    /* --- StompSessionHandler methods --- */

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("Connection to STOMP server established.\n" +
                "Session: {}\n" +
                "Headers: {}" + session + " " + connectedHeaders);
        // TODO: Here you can subscribe to a list of rooms which you want to consume messages from after a connection was established.
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("Got an exception while handling a frame.\n" +
                "Command: {}\n" +
                "Headers: {}\n" +
                "Payload: {}\n" +
                "{}"+ command+ headers+ payload+ exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.out.println("Retrieved a transport error: {}"+ session);
        exception.printStackTrace();
        if (!session.isConnected()) {
            subscriptions.clear();
            connect();
        }
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MessageDto.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("Got a new message {}"+ payload);
        MessageDto stompMessage = (MessageDto) payload;
        // TODO: Consume the message, handle possible ClassCastExceptions or different payload classes.
    }

    /**
     * Unsubscribe and close connection before destroying this instance (e.g. on application shutdown).
     */
    @PreDestroy
    void onShutDown() {
        for (String key : subscriptions.keySet()) {
            subscriptions.get(key).unsubscribe();
        }

        if (stompSession != null) {
            stompSession.disconnect();
        }
    }
}
