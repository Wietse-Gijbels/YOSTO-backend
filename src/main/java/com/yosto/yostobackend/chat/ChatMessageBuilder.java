package com.yosto.yostobackend.chat;

import java.util.Date;
import java.util.UUID;

public class ChatMessageBuilder {

    String chatRoomId;
    UUID senderId;
    UUID recipientId;
    String content;
    Date timestamp;

    public ChatMessageBuilder() {
    }

    public static ChatMessageBuilder chatMessageBuilder() {
        return new ChatMessageBuilder();
    }

    public ChatMessageBuilder chatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
        return this;
    }

    public ChatMessageBuilder senderId(UUID senderId) {
        this.senderId = senderId;
        return this;
    }

    public ChatMessageBuilder recipientId(UUID recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public ChatMessageBuilder content(String content) {
        this.content = content;
        return this;
    }

    public ChatMessageBuilder timestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ChatMessage build() {
        return new ChatMessage(this);
    }
}
