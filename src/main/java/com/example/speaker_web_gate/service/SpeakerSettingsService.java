package com.example.speaker_web_gate.service;

import com.example.speaker_web_gate.event.SessionEvent;
import com.example.speaker_web_gate.event.SpeakerParameterEvent;
import com.example.speaker_web_gate.gateway.WebsocketGateway;
import com.example.speaker_web_gate.model.SpeakerParameter;
import com.example.speaker_web_gate.model.SpeakerSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpeakerSettingsService {

    private final WebsocketGateway websocketGateway;
    private final Set<String> ids = new CopyOnWriteArraySet<>();
    private final ApplicationEventPublisher applicationEventPublisher;


    @ServiceActivator(inputChannel = "webSocketFlow.input")
    public void processSettingsChange(Message<SpeakerParameter> message) {
        log.info("Received settings update: {}", message);
        log.info("Setting is : {}", message.getPayload());
        sendSpeakerParameterEvent(message.getPayload());
    }

    @EventListener
    public void sendSpeakerSettingsUpdate(SpeakerSettings speakerSettings) {
        log.info("Sending settings update: {}", speakerSettings);
        ids.forEach(id -> {
            try {
                websocketGateway.sendMessage(id, speakerSettings);
            } catch (IllegalArgumentException e) {
                log.warn("Session {} expired. Removing from list", id);
                ids.remove(id);
            }
        });
    }

    @EventListener(SessionEvent.class)
    public void sentSpeakerParameterUpdate(SessionEvent sessionEvent) {
        log.info("Session id {} is closed: {}", sessionEvent.getSessionId(), sessionEvent.isClosed());
        if (sessionEvent.isClosed()) {
            ids.remove(sessionEvent.getSessionId());
        } else {
            ids.add(sessionEvent.getSessionId());
        }
    }

    public void sendSpeakerParameterEvent(SpeakerParameter speakerParameter) {
        log.info("Sending settings event: {}", speakerParameter);
        var event = new SpeakerParameterEvent(this, speakerParameter);
        applicationEventPublisher.publishEvent(event);
    }
}
