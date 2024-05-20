package com.yosto.yostobackend.gebruiker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface GebruikerRepository extends JpaRepository<Gebruiker, UUID> {

    Optional<Gebruiker> findByEmail (String email);

    @NonNull
    Optional<Gebruiker> findById (@NonNull UUID id);
}
