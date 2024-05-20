package com.yosto.yostobackend.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue
    private UUID id;
    private String chatRoomId;
    private UUID senderId;
    private UUID recipientId;
    private String content;
    private Date timestamp;

    public ChatMessage() {
    }

    public ChatMessage(ChatMessageBuilder builder) {
        this.chatRoomId = builder.chatRoomId;
        this.senderId = builder.senderId;
        this.recipientId = builder.recipientId;
        this.content = builder.content;
        this.timestamp = builder.timestamp;
    }

    public UUID getId() {
        return id;
    }

    public String getChatRoomId() {
        return chatRoomId;
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
