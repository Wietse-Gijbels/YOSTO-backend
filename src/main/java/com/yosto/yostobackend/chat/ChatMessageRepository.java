package com.yosto.yostobackend.chat;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
  List<ChatMessage> findByChatRoomIdAndStudierichtingId(String chatRoomId, UUID studierichtingId);
}
