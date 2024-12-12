package com.example.speaker_web_gate.service;

import com.example.speaker_web_gate.gateway.MqttGateway;
import com.example.speaker_web_gate.model.SpeakerParameter;
import com.example.speaker_web_gate.model.SpeakerSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MqttService {

    private final MqttGateway mqttGateway;
    private final ApplicationEventPublisher applicationEventPublisher;

    @ServiceActivator(inputChannel = "mqttFlow.input")
    public void processSettingsChange(Message<SpeakerSettings> message) {
        log.info("Received settings update: {}", message);
        sentSpeakerParameterEvent(message.getPayload());
    }

    @EventListener
    public void sentSpeakerParameterUpdate(SpeakerParameter speakerParameter) {
        mqttGateway.sendMessage("/" + speakerParameter.getType().toString().toLowerCase(), 2, Integer.toString(speakerParameter.getValue()));
    }

    private void sentSpeakerParameterEvent(SpeakerSettings speakerSettings) {
        log.info("Sending settings event: {}", speakerSettings);
        applicationEventPublisher.publishEvent(speakerSettings);
    }
}
