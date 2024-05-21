package com.yosto.yostobackend.chatroom;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
  Optional<ChatRoom> findBySenderIdAndRecipientId(UUID senderId, UUID recipientId);
}
