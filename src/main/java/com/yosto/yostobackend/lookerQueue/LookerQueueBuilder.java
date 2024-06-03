package com.yosto.yostobackend.lookerQueue;

import java.time.LocalDateTime;
import java.util.UUID;

public class LookerQueueBuilder {

    UUID lookerId;
    LocalDateTime timestamp;
    UUID studierichtingId;

    public LookerQueueBuilder() {
    }

    public static LookerQueueBuilder lookerQueueBuilder() {
        return new LookerQueueBuilder();
    }

    public LookerQueueBuilder looker(UUID lookerId) {
        this.lookerId = lookerId;
        return this;
    }

    public LookerQueueBuilder timestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public LookerQueueBuilder studierichtingId(UUID studierichtingId) {
        this.studierichtingId = studierichtingId;
        return this;
    }

    public LookerQueue build() {
        return new LookerQueue(this);
    }
}
