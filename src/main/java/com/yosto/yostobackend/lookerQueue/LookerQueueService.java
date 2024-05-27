package com.yosto.yostobackend.lookerQueue;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LookerQueueService {

    private final LookerQueueRepository lookerQueueRepository;

    public LookerQueueService(LookerQueueRepository lookerQueueRepository) {
        this.lookerQueueRepository = lookerQueueRepository;
    }

    public Optional<LookerQueue> getFirstLookerInQueue(UUID userId) {
        List<LookerQueue> lookerQueues = lookerQueueRepository.findAllByOrderByTimestampAsc();
        Optional<LookerQueue> lookerQueue = lookerQueues.stream()
                .filter(queue -> !queue.getLookerId().equals(userId))
                .findFirst();

        lookerQueue.ifPresent(lookerQueueRepository::delete);
        return lookerQueue;
    }

    public LookerQueue addLookerToQueue(UUID lookerId) {
        LookerQueue newLookerQueue = new LookerQueueBuilder()
                .looker(lookerId)
                .timestamp(LocalDateTime.now())
                .build();

        lookerQueueRepository.save(newLookerQueue);
        return newLookerQueue;
    }

    public Integer getAmountOfLookersInQueue() {
        return (int) lookerQueueRepository.count();
    }
}
