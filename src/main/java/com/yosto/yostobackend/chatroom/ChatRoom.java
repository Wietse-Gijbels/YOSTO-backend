package com.yosto.yostobackend.chatroom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue
    private UUID id;
    private String chatId;
    private UUID senderId;
    private UUID recipientId;

    public ChatRoom() {
    }

    public ChatRoom(ChatRoomBuilder builder) {
        this.chatId = builder.chatId;
        this.senderId = builder.senderId;
        this.recipientId = builder.recipientId;
    }

    public UUID getId() {
        return id;
    }

    public String getChatId() {
        return chatId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public UUID getRecipientId() {
        return recipientId;
    }
}
