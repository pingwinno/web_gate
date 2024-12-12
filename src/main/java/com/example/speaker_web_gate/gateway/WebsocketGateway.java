package com.example.speaker_web_gate.gateway;

import com.example.speaker_web_gate.model.SpeakerSettings;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@MessagingGateway
public interface WebsocketGateway {

    @Gateway(requestChannel = "webSocketFlow.output")
    void sendMessage(@Header(SimpMessageHeaderAccessor.SESSION_ID_HEADER) String sessionId, SpeakerSettings value);
}
