package com.yosto.yostobackend.gebruiker;

import org.springframework.stereotype.Service;

import java.util.List;
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
@Service
public class GebruikerService {

    private final GebruikerRepository gebruikerRepository;

    public GebruikerService(GebruikerRepository gebruikerRepository) {
        this.gebruikerRepository = gebruikerRepository;
    }

    public Gebruiker getGebruikerByEmail(String email) {
        return gebruikerRepository.findByEmail(email)
                .orElseThrow();
    }
}
