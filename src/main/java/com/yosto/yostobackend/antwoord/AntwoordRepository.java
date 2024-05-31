package com.yosto.yostobackend.antwoord;

import com.yosto.yostobackend.gebruiker.Gebruiker;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface AntwoordRepository extends JpaRepository<Antwoord, UUID> {
    List<Antwoord> findByGebruiker(Gebruiker gebruiker);
}
