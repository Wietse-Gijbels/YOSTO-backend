package com.yosto.yostobackend.chatroom;

import java.util.*;
import java.util.stream.Collectors;

import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.gebruiker.GebruikerService;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {
  private final ChatRoomRepository chatRoomRepository;
  private final GebruikerService gebruikerService;

  public ChatRoomService(ChatRoomRepository chatRoomRepository, GebruikerService gebruikerService) {
    this.chatRoomRepository = chatRoomRepository;
    this.gebruikerService = gebruikerService;
  }

  public Optional<String> getChatRoomId(
          UUID senderId,
          UUID recipientId,
          UUID studierichtingId,
          boolean createNewRoomIfNotExists
  ) {
    return chatRoomRepository
            .findBySenderIdAndRecipientIdAndStudierichtingId(senderId, recipientId, studierichtingId)
            .map(ChatRoom::getChatId)
            .or(
                    () -> {
                      if (createNewRoomIfNotExists) {
                        var chatId = createChatId(senderId, recipientId, studierichtingId);
                        return Optional.of(chatId);
                      }

                      return Optional.empty();
                    }
            );
  }

  private String createChatId(UUID senderId, UUID recipientId, UUID studierichtingId) {
    var chatId = String.format("%s_%s_%s", senderId, recipientId, studierichtingId);

    ChatRoom senderRecipient = ChatRoomBuilder
            .chatRoomBuilder()
            .chatId(chatId)
            .senderId(senderId)
            .recipientId(recipientId)
            .studierichtingId(studierichtingId)
            .build();

    ChatRoom recipientSender = ChatRoomBuilder
            .chatRoomBuilder()
            .chatId(chatId)
            .senderId(recipientId)
            .recipientId(senderId)
            .studierichtingId(studierichtingId)
            .build();

    chatRoomRepository.save(senderRecipient);
    chatRoomRepository.save(recipientSender);

    return chatId;
  }

  public List<ChatRoom> getMyChatRooms(UUID id) {
    List<ChatRoom> chatRooms = chatRoomRepository.findAll();
    Set<String> seenChatIds = new HashSet<>();

    return chatRooms.stream()
            .filter(chatRoom -> (chatRoom.getSenderId().equals(id) || chatRoom.getRecipientId().equals(id))
                    && !chatRoom.getSenderId().equals(chatRoom.getRecipientId())
                    && seenChatIds.add(chatRoom.getChatId()))
            .collect(Collectors.toList());
  }
}
