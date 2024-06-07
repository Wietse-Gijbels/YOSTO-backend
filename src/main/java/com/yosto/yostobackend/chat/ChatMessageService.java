package com.yosto.yostobackend.chat;

import com.yosto.yostobackend.chatroom.ChatRoomService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {
  private final ChatMessageRepository repository;
  private final ChatRoomService chatRoomService;
  private final SimpMessagingTemplate messagingTemplate;

  public ChatMessageService(
          ChatMessageRepository repository,
          ChatRoomService chatRoomService,
          SimpMessagingTemplate messagingTemplate
  ) {
    this.repository = repository;
    this.chatRoomService = chatRoomService;
    this.messagingTemplate = messagingTemplate;
  }

  public ChatMessage save(ChatMessage chatMessage) {
    var chatRoomId = chatRoomService
            .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), chatMessage.getStudierichtingId(), true)
            .orElseThrow();
    ChatMessage newChat = new ChatMessageBuilder()
            .chatRoomId(chatRoomId)
            .senderId(chatMessage.getSenderId())
            .recipientId(chatMessage.getRecipientId())
            .studierichtingId(chatMessage.getStudierichtingId())
            .content(chatMessage.getContent())
            .timestamp(chatMessage.getTimestamp())
            .build();
    repository.save(newChat);
    return newChat;
  }

  public List<ChatMessage> findChatMessages(String chatId) {
    var parts = chatId.split("_");
    var senderId = UUID.fromString(parts[0]);
    var recipientId = UUID.fromString(parts[1]);
    var studierichtingId = UUID.fromString(parts[2]);
    var chatRoomId = chatRoomService.getChatRoomId(senderId, recipientId, studierichtingId, false);
    return chatRoomId.map(id -> repository.findByChatRoomIdAndStudierichtingId(id, studierichtingId)).orElse(new ArrayList<>());
  }

  public void processMessage(ChatMessage chatMessage) {
    ChatMessage savedMsg = save(chatMessage);
    messagingTemplate.convertAndSendToUser(
            savedMsg.getRecipientId().toString(),
            "/queue/messages",
            new ChatNotificationBuilder()
                    .id(savedMsg.getId())
                    .senderId(savedMsg.getSenderId())
                    .recipientId(savedMsg.getRecipientId())
                    .content(savedMsg.getContent())
                    .timestamp(savedMsg.getTimestamp())
                    .studierichtingId(savedMsg.getStudierichtingId())
                    .build()
    );
  }
}
