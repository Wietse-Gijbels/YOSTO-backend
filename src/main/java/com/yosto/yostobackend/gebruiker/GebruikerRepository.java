package com.yosto.yostobackend.gebruiker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GebruikerRepository extends JpaRepository<Gebruiker, UUID> {

    Optional<Gebruiker> findByEmail (String email);

    List<Gebruiker> findAllByStatus(Status status);
}
