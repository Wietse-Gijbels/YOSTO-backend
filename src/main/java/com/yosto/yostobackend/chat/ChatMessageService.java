package com.yosto.yostobackend.chat;

import com.yosto.yostobackend.chatroom.ChatRoomService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        var chatId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow();
        ChatMessage newChat = new ChatMessageBuilder()
                .chatId(chatId)
                .senderId(chatMessage.getSenderId())
                .recipientId(chatMessage.getRecipientId())
                .content(chatMessage.getContent())
                .timestamp(chatMessage.getTimestamp())
                .build();
        repository.save(newChat);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatId.map(repository::findByChatId).orElse(new ArrayList<>());
    }

    public void processMessage(ChatMessage chatMessage) {
        ChatMessage savedMsg = save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(), "/chat",
                new ChatNotificationBuilder()
                        .id(savedMsg.getId())
                        .senderId(savedMsg.getSenderId())
                        .recipientId(savedMsg.getRecipientId())
                        .content(savedMsg.getContent())
                        .build()
        );
    }
}
