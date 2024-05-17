package com.yosto.yostobackend.chat;

import com.yosto.yostobackend.chatroom.ChatRoomService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatMessageService(ChatMessageRepository repository, ChatRoomService chatRoomService, SimpMessagingTemplate messagingTemplate) {
        this.repository = repository;
        this.chatRoomService = chatRoomService;
        this.messagingTemplate = messagingTemplate;
    }

    public ChatMessage save(ChatMessage chatMessage) {
        var chatRoomId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow();
        ChatMessage newChat = new ChatMessageBuilder()
                .chatRoomId(chatRoomId)
                .senderId(chatMessage.getSenderId())
                .recipientId(chatMessage.getRecipientId())
                .content(chatMessage.getContent())
                .timestamp(chatMessage.getTimestamp())
                .build();
        repository.save(newChat);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(UUID senderId, UUID recipientId) {
        var chatRoomId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatRoomId.map(repository::findByChatId).orElse(new ArrayList<>());
    }

    public void processMessage(ChatMessage chatMessage) {
        ChatMessage savedMsg = save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId().toString(), "/queue/messages",
                new ChatNotificationBuilder()
                        .id(savedMsg.getId())
                        .senderId(savedMsg.getSenderId())
                        .recipientId(savedMsg.getRecipientId())
                        .content(savedMsg.getContent())
                        .build()
        );
    }
}
