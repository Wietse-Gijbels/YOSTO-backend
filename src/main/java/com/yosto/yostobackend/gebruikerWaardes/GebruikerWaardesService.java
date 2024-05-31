package com.yosto.yostobackend.gebruikerWaardes;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GebruikerWaardesService {

    private final GebruikerWaardesRepository gebruikerWaardesRepository;

    public GebruikerWaardesService(GebruikerWaardesRepository gebruikerWaardesRepository) {
        this.gebruikerWaardesRepository = gebruikerWaardesRepository;
    }

    public GebruikerWaardes findByGebruikerId(UUID id) {
        return gebruikerWaardesRepository.findByGebruikerId(id);
    }
}
