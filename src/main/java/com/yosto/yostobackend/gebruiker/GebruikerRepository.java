package com.yosto.yostobackend.gebruiker;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface GebruikerRepository extends JpaRepository<Gebruiker, UUID> {
  Optional<Gebruiker> findByEmail(String email);

  @NonNull
  Optional<Gebruiker> findById(@NonNull UUID id);
}
