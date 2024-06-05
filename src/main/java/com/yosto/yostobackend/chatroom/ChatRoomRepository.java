package com.yosto.yostobackend.chatroom;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
  Optional<ChatRoom> findBySenderIdAndRecipientIdAndStudierichtingId(UUID senderId, UUID recipientId, UUID studierichtingId);

  Optional<ChatRoom> findByChatId(String chatId);
}
