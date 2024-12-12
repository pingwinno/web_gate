package com.example.speaker_web_gate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class SpeakerWebGateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpeakerWebGateApplication.class, args);
    }

}
