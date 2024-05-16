package com.yosto.yostobackend.chatroom;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_room")
public class ChatRoom {

    @Id
    private Long id;
    private String chatId;
    private String senderId;
    private String recipientId;

    public ChatRoom() {
    }

    public ChatRoom(ChatRoomBuilder builder) {
        this.chatId = builder.chatId;
        this.senderId = builder.senderId;
        this.recipientId = builder.recipientId;
    }

    public Long getId() {
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
}
