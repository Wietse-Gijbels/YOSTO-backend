package com.yosto.yostobackend.chatroom;

import jakarta.persistence.*;

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
  private UUID studierichtingId;
  private String studierichtingNaam;
  private String studiepunten;
  private Boolean isAfgesloten;

  public ChatRoom() {}

  public ChatRoom(ChatRoomBuilder builder) {
    this.chatId = builder.chatId;
    this.senderId = builder.senderId;
    this.recipientId = builder.recipientId;
    this.studierichtingId = builder.studierichtingId;
    this.studierichtingNaam = builder.studierichtingNaam;
    this.studiepunten = builder.studiepunten;
    this.isAfgesloten = builder.isAfgesloten;
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

  public UUID getStudierichtingId() {
    return studierichtingId;
  }

  public String getStudierichtingNaam() {
      return studierichtingNaam;
  }

  public String getStudiepunten() {
      return studiepunten;
  }

  public Boolean getIsAfgesloten() {
    return isAfgesloten;
  }
}
