package com.yosto.yostobackend.lookerQueue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LookerQueueRepository extends JpaRepository<LookerQueue, String>{

    Optional<LookerQueue> findFirstByOrderByTimestampAsc();
    List<LookerQueue> findAllByOrderByTimestampAsc();
}