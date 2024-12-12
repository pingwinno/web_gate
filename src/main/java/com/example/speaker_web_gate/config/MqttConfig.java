package com.example.speaker_web_gate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.Mqttv5PahoMessageHandler;


@Configuration
public class MqttConfig {

    @Value("${mqtt.url}")
    private String mqttHost;

    @Value("${mqtt.port}")
    private int mqttPort;


    @Bean
    public IntegrationFlow mqttOutFlow() {
        Mqttv5PahoMessageHandler messageHandler = new Mqttv5PahoMessageHandler("tcp://" + mqttHost + ":" + mqttPort, "web_gate_out");
        messageHandler.setAsync(true);
        messageHandler.setAsyncEvents(true);
        return f -> f
                .channel("mqttFlow.output")
                .handle(messageHandler);
    }

    @Bean
    public IntegrationFlow mqttInFlow() {
        Mqttv5PahoMessageDrivenChannelAdapter messageProducer =
                new Mqttv5PahoMessageDrivenChannelAdapter("tcp://" + mqttHost + ":" + mqttPort, "web_gate_in", "/settings");
        messageProducer.setPayloadType(byte[].class);
        return IntegrationFlow.from(messageProducer)
                .channel(c -> c.queue("mqttFlow.input"))
                .get();
    }

}
