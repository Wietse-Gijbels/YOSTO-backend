package com.yosto.yostobackend.gebruiker;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GebruikerService {

    private final GebruikerRepository repository;

    public GebruikerService(GebruikerRepository repository) {
        this.repository = repository;
    }

    public void disconnect(Gebruiker gebruiker) {
        repository.findById(gebruiker.getId()).orElseThrow();
        gebruiker.disconnect();
        repository.save(gebruiker);
    }

    public List<Gebruiker> findConnectedGebruikers() {
        return repository.findAllByStatus(Status.ONLINE);
    }

    public Gebruiker getGebruikerByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow();
    }

    public Gebruiker getById(UUID id) {
        return repository.findById(id).orElseThrow();
    }
}
