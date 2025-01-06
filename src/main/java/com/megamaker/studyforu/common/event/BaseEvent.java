package com.megamaker.studyforu.common.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEvent {
    private final LocalDateTime createdAt;

    public BaseEvent() {
        this.createdAt = LocalDateTime.now();
    }
}
