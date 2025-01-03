package com.example.speaker_web_gate.config;

import com.example.speaker_web_gate.event.SessionEvent;
import com.example.speaker_web_gate.event.SpeakerParameterEvent;
import com.example.speaker_web_gate.model.SpeakerParameter;
import com.example.speaker_web_gate.model.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.websocket.ServerWebSocketContainer;
import org.springframework.integration.websocket.inbound.WebSocketInboundChannelAdapter;
import org.springframework.integration.websocket.outbound.WebSocketOutboundMessageHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

@Configuration
@RequiredArgsConstructor
@Component
public class WebSocketConfig {

    private final ApplicationEventPublisher applicationEventPublisher;


    @Bean
    ServerWebSocketContainer serverWebSocketContainer() {
        return new ServerWebSocketContainer("/ws");
    }

    @Bean
    IntegrationFlow webSocketOutFlow() {
        var webSocketOutboundMessageHandler = new WebSocketOutboundMessageHandler(serverWebSocketContainer());
        return flow -> flow
                .channel("webSocketFlow.output")
                .handle(webSocketOutboundMessageHandler);
    }


    @Bean
    IntegrationFlow webSocketInFlow() {
        WebSocketInboundChannelAdapter adapter = new WebSocketInboundChannelAdapter(serverWebSocketContainer()) {
            @Override
            public void afterSessionStarted(WebSocketSession session) {
                super.afterSessionStarted(session);
                var sessionEvent = new SessionEvent(this, session.getId(), false);
                applicationEventPublisher.publishEvent(sessionEvent);
                var speakerParameter = new SpeakerParameter();
                speakerParameter.setType(Type.GET);
                speakerParameter.setValue(0);
                var event = new SpeakerParameterEvent(this, speakerParameter);
                applicationEventPublisher.publishEvent(event);
            }

            @Override
            public void afterSessionEnded(WebSocketSession session, CloseStatus closeStatus) {
                super.afterSessionEnded(session, closeStatus);
                var sessionEvent = new SessionEvent(this, session.getId(), true);
                applicationEventPublisher.publishEvent(sessionEvent);
            }
        };
        return IntegrationFlow.from(adapter)
                .channel("webSocketFlow.input")
                .get();

    }
}
