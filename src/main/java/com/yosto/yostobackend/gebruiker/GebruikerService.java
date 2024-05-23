package com.yosto.yostobackend.gebruiker;

import com.yosto.yostobackend.generic.ServiceException;

import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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
        Map<String, String> errors = new HashMap<>();

        return repository
                .findByEmail(email)
                .orElseThrow(
                        () -> {
                            errors.put("errorFindByEmail", "Er bestaat geen gebruiker met deze email.");
                            return new ServiceException(errors);
                        }
                );
    }

    public Gebruiker getGebruikerById(UUID id) {
        Map<String, String> errors = new HashMap<>();

        return repository
                .findById(id)
                .orElseThrow(
                        () -> {
                            errors.put("errorFindById", "Er bestaat geen gebruiker met deze id.");
                            return new ServiceException(errors);
                        }
                );
    }

    public Gebruiker updateGebruiker(String email, UpdateGebruikerDTO gebruiker) {
        Gebruiker oudeGebruiker = getGebruikerByEmail(email);
        return repository.save(new GebruikerBuilder()
                .setId(oudeGebruiker.getId())
                .setVoornaam(gebruiker.voornaam())
                .setAchternaam(gebruiker.achternaam())
                .setEmail(gebruiker.email())
                .setWoonplaats(gebruiker.woonplaats())
                .setStatus(oudeGebruiker.getStatus())
                .setRol(oudeGebruiker.getRollen().stream().toList().get(0))
                .setLeeftijd(oudeGebruiker.getLeeftijd())
                .setGeslacht(oudeGebruiker.getGeslacht())
                .setWachtwoord(oudeGebruiker.getWachtwoord())
                .build());
    }
}
