package com.example.speaker_web_gate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.websocket.ServerWebSocketContainer;
import org.springframework.integration.websocket.inbound.WebSocketInboundChannelAdapter;
import org.springframework.integration.websocket.outbound.WebSocketOutboundMessageHandler;

@Configuration
public class WebSocketConfig {

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
        WebSocketInboundChannelAdapter adapter = new WebSocketInboundChannelAdapter(serverWebSocketContainer());
        return IntegrationFlow.from(adapter)
                .channel("webSocketFlow.input")
                .get();

    }
}
