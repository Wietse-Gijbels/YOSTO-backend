package com.yosto.yostobackend.chat;

import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public class ChatNotification {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID senderId;
    private UUID recipientId;
    private String content;

    public ChatNotification() {

    }

    public ChatNotification(ChatNotificationBuilder builder) {
        this.id = builder.id;
        this.senderId = builder.senderId;
        this.recipientId = builder.recipientId;
        this.content = builder.content;
    }
}
