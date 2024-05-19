package com.yosto.yostobackend.gebruiker;

import com.yosto.yostobackend.generic.ServiceException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class GebruikerService {

    private final GebruikerRepository gebruikerRepository;

    public GebruikerService(GebruikerRepository gebruikerRepository) {
        this.gebruikerRepository = gebruikerRepository;
    }

    public Gebruiker getGebruikerByEmail(String email) {
        Map<String, String> errors = new HashMap<>();

        return gebruikerRepository.findByEmail(email).orElseThrow(() -> {
            errors.put("errorFindByEmail", "Er bestaat geen gebruiker met deze email.");
            return new ServiceException(errors);
        });
    }

    public Gebruiker getGebruikerById(UUID id) {
        Map<String, String> errors = new HashMap<>();

        return gebruikerRepository.findById(id).orElseThrow(() -> {
            errors.put("errorFindById", "Er bestaat geen gebruiker met deze id.");
            return new ServiceException(errors);
        });
    }
}
