package com.yosto.yostobackend.chat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;

import java.util.UUID;
import java.util.Date;

@JsonSerialize
@JsonDeserialize
public class ChatNotification {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID senderId;
    private UUID recipientId;
    private String content;
    private Date timestamp;

    public ChatNotification() {

    }

    public ChatNotification(ChatNotificationBuilder builder) {
        this.id = builder.id;
        this.senderId = builder.senderId;
        this.recipientId = builder.recipientId;
        this.content = builder.content;
        this.timestamp = builder.timestamp;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public UUID getRecipientId() {
        return recipientId;
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
