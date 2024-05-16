package com.yosto.yostobackend.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    private UUID id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String content;
    private Date timestamp;

    public ChatMessage() {
    }

    public ChatMessage(ChatMessageBuilder builder) {
        this.chatId = builder.chatId;
        this.senderId = builder.senderId;
        this.recipientId = builder.recipientId;
        this.content = builder.content;
        this.timestamp = builder.timestamp;
    }

    public UUID getId() {
        return id;
    }

    public String getChatId() {
        return chatId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
