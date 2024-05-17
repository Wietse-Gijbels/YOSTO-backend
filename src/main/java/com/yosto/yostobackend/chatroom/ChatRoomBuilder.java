package com.yosto.yostobackend.chatroom;

import java.util.UUID;

public class ChatRoomBuilder {

    String chatId;
    UUID senderId;
    UUID recipientId;

    public ChatRoomBuilder() {
    }

    public static ChatRoomBuilder chatRoomBuilder() {
        return new ChatRoomBuilder();
    }

    public ChatRoomBuilder chatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public ChatRoomBuilder senderId(UUID senderId) {
        this.senderId = senderId;
        return this;
    }

    public ChatRoomBuilder recipientId(UUID recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public ChatRoom build() {
        return new ChatRoom(this);
    }
}
