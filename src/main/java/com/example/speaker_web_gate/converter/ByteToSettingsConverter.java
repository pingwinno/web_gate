package com.example.speaker_web_gate.converter;

import com.example.speaker_web_gate.model.SpeakerSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.integration.config.IntegrationConverter;
import org.springframework.stereotype.Component;

@IntegrationConverter
@Component
@RequiredArgsConstructor

public class ByteToSettingsConverter implements Converter<byte[], SpeakerSettings> {

    private final ObjectMapper objectMapper;
    @SneakyThrows
    @Override
    public SpeakerSettings convert(byte[] source) {
        return objectMapper.readValue(source, SpeakerSettings.class);
    }
}
