package com.example.speaker_web_gate.controller;

import com.example.speaker_web_gate.model.Setting;
import com.example.speaker_web_gate.model.Settings;
import com.example.speaker_web_gate.service.SpeakerSettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SpeakerController {

    private final SpeakerSettingsService speakerSettingsService;

    @MessageMapping("/set")
    @SendTo("/speaker/settings")
    public Settings greeting(Setting message) {
        return speakerSettingsService.processSettingChange(message);
    }

}
