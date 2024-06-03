package com.yosto.yostobackend.lookerQueue;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "looker_queue")
public class LookerQueue {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID lookerId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private UUID studierichtingId;

    // Constructors, getters and setters

    public LookerQueue() {
    }

    public LookerQueue(LookerQueueBuilder builder) {
        this.lookerId = builder.lookerId;
        this.timestamp = builder.timestamp;
        this.studierichtingId = builder.studierichtingId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getLookerId() {
        return lookerId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UUID getStudierichtingId() {
        return studierichtingId;
    }
}
