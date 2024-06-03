package com.yosto.yostobackend.lookerQueue;

import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.gebruiker.GebruikerService;
import org.springframework.stereotype.Service;

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

    public LookerQueueService(LookerQueueRepository lookerQueueRepository, GebruikerService gebruikerService) {
        this.lookerQueueRepository = lookerQueueRepository;
        this.gebruikerService = gebruikerService;
    }

    public Optional<LookerQueue> getFirstLookerInQueue(UUID userId) {
        Gebruiker helper = gebruikerService.getGebruikerById(userId);
        Set<UUID> completedStudyDirections = helper.getBehaaldeDiplomas().stream().map(studierichting -> studierichting.getId()).collect(Collectors.toSet());

        List<LookerQueue> lookerQueues = lookerQueueRepository.findAllByOrderByTimestampAsc();
        Optional<LookerQueue> lookerQueue = lookerQueues.stream()
                .filter(queue -> !queue.getLookerId().equals(userId) && completedStudyDirections.contains(queue.getStudierichtingId()))
                .findFirst();

        lookerQueue.ifPresent(lookerQueueRepository::delete);
        return lookerQueue;
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
