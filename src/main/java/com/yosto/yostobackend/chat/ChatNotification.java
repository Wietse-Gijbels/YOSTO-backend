package com.yosto.yostobackend.chat;

import org.springframework.data.annotation.Id;

public class ChatNotification {

    @Id
    private String id;
    private String senderId;
    private String recipientId;
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
