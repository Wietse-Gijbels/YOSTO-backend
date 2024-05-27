package com.yosto.yostobackend.studierichting;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudierichtingRepository extends PagingAndSortingRepository<Studierichting, UUID> {
    Optional<Studierichting> findByNaamAndNiveauNaam(String naam, String niveauNaam);
}
