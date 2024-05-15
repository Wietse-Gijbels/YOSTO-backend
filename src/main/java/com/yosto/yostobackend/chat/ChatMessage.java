package com.yosto.yostobackend.chat;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class ChatMessage {
    @Id
    private String id;
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

    public String getId() {
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
