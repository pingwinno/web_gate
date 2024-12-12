package com.example.speaker_web_gate.converter;

import com.example.speaker_web_gate.model.SpeakerParameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.integration.config.IntegrationConverter;
import org.springframework.stereotype.Component;

@IntegrationConverter
@Component
@RequiredArgsConstructor
public class StringToSettingConverter implements Converter<String, SpeakerParameter> {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public SpeakerParameter convert(String source) {
        return objectMapper.readValue(source, SpeakerParameter.class);
    }
}
