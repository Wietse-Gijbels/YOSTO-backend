package com.yosto.yostobackend.lookerQueue;

import com.yosto.yostobackend.chatroom.ChatRoom;
import com.yosto.yostobackend.chatroom.ChatRoomRepository;
import com.yosto.yostobackend.chatroom.ChatRoomService;
import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.gebruiker.GebruikerService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LookerQueueService {

    private final LookerQueueRepository lookerQueueRepository;
    private final GebruikerService gebruikerService;

    private final ChatRoomService chatRoomService;

    private final ChatRoomRepository chatRoomRepository;

    public LookerQueueService(LookerQueueRepository lookerQueueRepository, GebruikerService gebruikerService, ChatRoomService chatRoomService, ChatRoomRepository chatRoomRepository) {
        this.lookerQueueRepository = lookerQueueRepository;
        this.gebruikerService = gebruikerService;
        this.chatRoomService = chatRoomService;
        this.chatRoomRepository = chatRoomRepository;
    }

    public Optional<String> getChatForFirstLookerInQueue(UUID userId) {
        Gebruiker helper = gebruikerService.getGebruikerById(userId);
        Set<UUID> completedStudyDirections = helper.getBehaaldeDiplomas().stream().map(studierichting -> studierichting.getId()).collect(Collectors.toSet());

        List<String> myChatRoomChatIds = chatRoomService.getMyChatRooms(userId).stream().map(ChatRoom::getChatId).toList();

        List<LookerQueue> lookerQueues = lookerQueueRepository.findAllByOrderByTimestampAsc();
        Optional<LookerQueue> lookerQueue = lookerQueues.stream()
                .filter(queue -> !queue.getLookerId().equals(userId) && completedStudyDirections.contains(queue.getStudierichtingId()) && !myChatRoomChatIds.contains(String.format("%s_%s_%s", userId, queue.getLookerId(), queue.getStudierichtingId()))) //check if they already have a chat, what would the chatroom id be with user, looker and studierichting? and compare
                .findFirst();

        if (lookerQueue.isPresent()) {
            lookerQueue.ifPresent(lookerQueueRepository::delete);
            Optional<String> chatRoomChatId = chatRoomService.getChatRoomId(userId, lookerQueue.get().getLookerId(), lookerQueue.get().getStudierichtingId(), true);
            //get the chatroom id from the chatroom chatId
            return chatRoomChatId;
        } else {
            return Optional.empty();
        }
    }

    public LookerQueue addLookerToQueue(UUID lookerId, UUID studierichtingId) {
        LookerQueue newLookerQueue = new LookerQueueBuilder()
                .looker(lookerId)
                .timestamp(LocalDateTime.now())
                .studierichtingId(studierichtingId)
                .build();

        lookerQueueRepository.save(newLookerQueue);
        return newLookerQueue;
    }

    public Integer getAmountOfLookersInQueue() {
        return (int) lookerQueueRepository.count();
    }
}
