package com.yosto.yostobackend.gebruiker;

import org.springframework.stereotype.Service;

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
