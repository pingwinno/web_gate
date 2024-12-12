package com.example.speaker_web_gate.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Data
public class SpeakerSettings {

    private int volume;
    private int input;
    private int sw;
    private int treble;
    private int bass;
    private int balance;
    private int state;
}
