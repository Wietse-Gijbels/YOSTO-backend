package com.yosto.yostobackend.chatroom;

import java.util.UUID;

public class ChatRoomBuilder {
  UUID id;
  String chatId;
  UUID senderId;
  UUID recipientId;
  UUID studierichtingId;
  String studierichtingNaam;
  Boolean isAfgesloten;

  public ChatRoomBuilder() {}

  public static ChatRoomBuilder chatRoomBuilder() {
    return new ChatRoomBuilder();
  }

  public ChatRoomBuilder id(UUID id) {
      this.id = id;
      return this;
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

  public ChatRoomBuilder studierichtingId(UUID studierichtingId) {
    this.studierichtingId = studierichtingId;
    return this;
  }

  public ChatRoomBuilder studierichtingNaam(String studierichtingNaam) {
    this.studierichtingNaam = studierichtingNaam;
    return this;
  }

  public ChatRoomBuilder isAfgesloten(Boolean isAfgesloten) {
      this.isAfgesloten = isAfgesloten;
      return this;
  }

  public ChatRoom build() {
    return new ChatRoom(this);
  }
}
