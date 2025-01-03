package com.example.speaker_web_gate.event;

import com.example.speaker_web_gate.model.SpeakerParameter;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SpeakerParameterEvent extends ApplicationEvent {
    private final SpeakerParameter speakerParameter;

    public SpeakerParameterEvent(Object source, SpeakerParameter speakerParameter) {
        super(source);
        this.speakerParameter = speakerParameter;
    }

}
