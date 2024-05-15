package com.yosto.yostobackend.gebruiker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GebruikerRepository extends JpaRepository<Gebruiker, UUID> {

    Optional<Gebruiker> findByEmail (String email);
}
