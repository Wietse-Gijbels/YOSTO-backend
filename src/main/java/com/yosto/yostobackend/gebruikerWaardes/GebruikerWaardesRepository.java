package com.yosto.yostobackend.gebruikerWaardes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GebruikerWaardesRepository extends JpaRepository<GebruikerWaardes, UUID> {
    GebruikerWaardes findByGebruikerId(UUID id);
}
