package com.yosto.yostobackend.chatroom;

import java.util.*;

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
    boolean createNewRoomIfNotExists
  ) {
    return chatRoomRepository
      .findBySenderIdAndRecipientId(senderId, recipientId)
      .map(ChatRoom::getChatId)
      .or(
        () -> {
          if (createNewRoomIfNotExists) {
            var chatId = createChatId(senderId, recipientId);
            return Optional.of(chatId);
          }

          return Optional.empty();
        }
      );
  }

  private String createChatId(UUID senderId, UUID recipientId) {
    var chatId = String.format("%s_%s", senderId, recipientId);

    ChatRoom senderRecipient = ChatRoomBuilder
      .chatRoomBuilder()
      .chatId(chatId)
      .senderId(senderId)
      .recipientId(recipientId)
      .build();

    ChatRoom recipientSender = ChatRoomBuilder
      .chatRoomBuilder()
      .chatId(chatId)
      .senderId(recipientId)
      .recipientId(senderId)
      .build();

    chatRoomRepository.save(senderRecipient);
    chatRoomRepository.save(recipientSender);

    return chatId;
  }
  public List<Gebruiker> getMyChatRooms(UUID id) {
    Set<Gebruiker> users = new HashSet<>();

    List<ChatRoom> chatRooms = chatRoomRepository.findAll();

    for (ChatRoom chatRoom : chatRooms) {
      if (chatRoom.getSenderId().equals(id)) {
        users.add(gebruikerService.getGebruikerById(chatRoom.getRecipientId()));
      } else if (chatRoom.getRecipientId().equals(id)) {
        users.add(gebruikerService.getGebruikerById(chatRoom.getSenderId()));
      }
    }

    return new ArrayList<>(users);
  }
}
