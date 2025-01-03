package com.example.speaker_web_gate.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SessionEvent extends ApplicationEvent {
    private final String sessionId;
    private final boolean isClosed;

    public SessionEvent(Object source, String sessionId, boolean isClosed) {
        super(source);
        this.sessionId = sessionId;
        this.isClosed = isClosed;
    }

}
