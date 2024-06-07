package com.yosto.yostobackend.chatroom;

import java.util.*;
import java.util.stream.Collectors;

import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.gebruiker.GebruikerService;
import com.yosto.yostobackend.studierichting.StudierichtingService;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {
  private final ChatRoomRepository chatRoomRepository;
  private final GebruikerService gebruikerService;
  private final StudierichtingService studierichtingService;

  public ChatRoomService(ChatRoomRepository chatRoomRepository, GebruikerService gebruikerService, StudierichtingService studierichtingService) {
    this.chatRoomRepository = chatRoomRepository;
    this.gebruikerService = gebruikerService;
    this.studierichtingService = studierichtingService;
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
            .studierichtingNaam(studierichtingService.getStudierichtingById(studierichtingId).getNaam())
            .isAfgesloten(false)
            .build();

    ChatRoom recipientSender = ChatRoomBuilder
            .chatRoomBuilder()
            .chatId(chatId)
            .senderId(recipientId)
            .recipientId(senderId)
            .studierichtingId(studierichtingId)
            .studierichtingNaam(studierichtingService.getStudierichtingById(studierichtingId).getNaam())
            .isAfgesloten(false)
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


  public void sluitChat(String chatId) {
    List<ChatRoom> chatRooms = chatRoomRepository.findByChatId(chatId).stream().collect(Collectors.toList());

    if (chatRooms.size() == 2) {
      chatRoomRepository.deleteAll(chatRooms); // Delete old chat rooms

      List<ChatRoom> updatedChatRooms = chatRooms.stream().map(chatRoom -> ChatRoomBuilder
              .chatRoomBuilder()
              .id(chatRoom.getId())
              .chatId(chatRoom.getChatId())
              .senderId(chatRoom.getSenderId())
              .recipientId(chatRoom.getRecipientId())
              .studierichtingId(chatRoom.getStudierichtingId())
              .studierichtingNaam(chatRoom.getStudierichtingNaam())
              .isAfgesloten(true)
              .build()).collect(Collectors.toList());

      chatRoomRepository.saveAll(updatedChatRooms); // Save new chat rooms with isAfgesloten set to true
    }
  }

  public ChatRoom getChat(String chatId) {
    return chatRoomRepository.findByChatId(chatId).stream().findFirst().orElse(null);
  }
}
