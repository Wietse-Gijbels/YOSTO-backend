package com.yosto.yostobackend.studierichting;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface StudierichtingRepository extends PagingAndSortingRepository<Studierichting, UUID> {

    Studierichting findById(UUID id);
}
