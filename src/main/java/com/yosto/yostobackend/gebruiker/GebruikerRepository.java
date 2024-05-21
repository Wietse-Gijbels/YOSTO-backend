package com.yosto.yostobackend.gebruiker;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface GebruikerRepository extends JpaRepository<Gebruiker, UUID> {
  Optional<Gebruiker> findByEmail(String email);

  @NonNull
  Optional<Gebruiker> findById(@NonNull UUID id);

  List<Gebruiker> findAllByStatus(Status status);
}
