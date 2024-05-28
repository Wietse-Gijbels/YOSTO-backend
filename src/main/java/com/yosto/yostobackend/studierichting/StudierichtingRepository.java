package com.yosto.yostobackend.studierichting;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudierichtingRepository extends PagingAndSortingRepository<Studierichting, UUID> {
    Optional<Studierichting> findByNaamAndNiveauNaam(String naam, String niveauNaam);

    List<Studierichting> findAll();
}
