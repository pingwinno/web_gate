package com.example.speaker_web_gate.service;

import com.example.speaker_web_gate.model.Setting;
import com.example.speaker_web_gate.model.Settings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpeakerSettingsService {
    public Settings processSettingChange(Setting setting) {
        var settings = new Settings();
        log.info("Received message: {}", setting);
        switch (setting.getType()) {
            case VOLUME -> settings.setVolume(setting.getValue());
            case SW -> settings.setSw(setting.getValue());
            case BALANCE -> settings.setBalance(setting.getValue());
            case STATE -> settings.setState(setting.getValue());
            case INPUT -> settings.setInput(setting.getValue());
            case BASS -> settings.setBass(setting.getValue());
            case TREBLE -> settings.setTreble(setting.getValue());
        }
        log.info("Sending message: {}", settings);
        return settings;
    }
}
