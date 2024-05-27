package com.yosto.yostobackend.lookerQueue;

import com.yosto.yostobackend.gebruiker.Gebruiker;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "looker_queue")
public class LookerQueue {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = false)
    private UUID lookerId;

    @Column(nullable = false, unique = true)
    private LocalDateTime timestamp;

    public LookerQueue() {
    }

    public LookerQueue(LookerQueueBuilder builder) {
        this.lookerId = builder.lookerId;
        this.timestamp = builder.timestamp;
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
}
