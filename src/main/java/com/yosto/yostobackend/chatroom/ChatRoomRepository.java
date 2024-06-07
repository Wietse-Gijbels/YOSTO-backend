package com.yosto.yostobackend.chatroom;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
  Optional<ChatRoom> findBySenderIdAndRecipientIdAndStudierichtingId(UUID senderId, UUID recipientId, UUID studierichtingId);

  List<ChatRoom> findByChatId(String chatId);
}
