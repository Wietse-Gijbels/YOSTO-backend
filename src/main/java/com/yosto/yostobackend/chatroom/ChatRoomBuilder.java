package com.yosto.yostobackend.chatroom;

public class ChatRoomBuilder {

    String chatId;
    String senderId;
    String recipientId;

    public ChatRoomBuilder() {
    }

    public static ChatRoomBuilder chatRoomBuilder() {
        return new ChatRoomBuilder();
    }

    public ChatRoomBuilder chatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public ChatRoomBuilder senderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public ChatRoomBuilder recipientId(String recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public ChatRoom build() {
        return new ChatRoom(this);
    }
}
