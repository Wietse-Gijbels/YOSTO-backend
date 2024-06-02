package com.yosto.yostobackend.gebruikerWaardes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public GebruikerWaardes save(GebruikerWaardes gebruikerWaardes) {
        GebruikerWaardes existingGebruikerWaardes = gebruikerWaardesRepository.findByGebruikerId(gebruikerWaardes.getGebruiker().getId());
        if (existingGebruikerWaardes != null) {
            // Delete the existing record
            gebruikerWaardesRepository.delete(existingGebruikerWaardes);

            // Build a new record with the combined data
            gebruikerWaardes = new GebruikerWaardesBuilder()
                    .setId(existingGebruikerWaardes.getId())
                    .setGebruiker(gebruikerWaardes.getGebruiker())
                    .setConventioneel(gebruikerWaardes.getConventioneel())
                    .setPraktisch(gebruikerWaardes.getPraktisch())
                    .setAnalytisch(gebruikerWaardes.getAnalytisch())
                    .setKunstzinnig(gebruikerWaardes.getKunstzinnig())
                    .setSociaal(gebruikerWaardes.getSociaal())
                    .setOndernemend(gebruikerWaardes.getOndernemend())
                    .build();
        }
        return gebruikerWaardesRepository.save(gebruikerWaardes);
    }
}
