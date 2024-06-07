package com.yosto.yostobackend.gebruikerWaardes;

import com.yosto.yostobackend.antwoord.Antwoord;
import com.yosto.yostobackend.antwoord.AntwoordRepository;
import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.generic.ServiceException;
import com.yosto.yostobackend.notifications.NotificationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GebruikerWaardesService {

    private final GebruikerWaardesRepository gebruikerWaardesRepository;

    private final AntwoordRepository antwoordRepository;


    public GebruikerWaardesService(GebruikerWaardesRepository gebruikerWaardesRepository, AntwoordRepository antwoordRepository) {
        this.gebruikerWaardesRepository = gebruikerWaardesRepository;
        this.antwoordRepository = antwoordRepository;
    }

    public GebruikerWaardes findByGebruikerId(UUID id) {
        Map<String, String> errors = new HashMap<>();

        GebruikerWaardes gebruikersWaardes = gebruikerWaardesRepository.findByGebruikerId(id);
        if (gebruikersWaardes == null) {
            errors.put("errorGebruikerWaardes", "Er zijn nog geen waardes voor deze gebruiker");
            throw new ServiceException(errors);
        }
        return gebruikersWaardes;
    }

    public GebruikerWaardes calculateGebruikerWaardes(Gebruiker gebruiker) {
        if (gebruikerWaardesRepository.findByGebruikerId(gebruiker.getId()) != null) {
            return gebruikerWaardesRepository.findByGebruikerId(gebruiker.getId());
        }

        List<Antwoord> antwoorden = antwoordRepository.findByGebruiker(gebruiker);

        Map<String, Integer> scores = antwoorden.stream()
                .collect(Collectors.toMap(
                        a -> a.getVraag().getParameter(),
                        a -> calculateScore(a.getAntwoord()),
                        Integer::sum));

        GebruikerWaardes gebruikerWaardes = new GebruikerWaardesBuilder()
                .setGebruiker(gebruiker)
                .setConventioneel(scores.getOrDefault("conventioneel", 50) + 50)
                .setPraktisch(scores.getOrDefault("praktisch", 50) + 50)
                .setAnalytisch(scores.getOrDefault("analytisch", 50) + 50)
                .setKunstzinnig(scores.getOrDefault("kunstzinnig", 50) + 50)
                .setSociaal(scores.getOrDefault("sociaal", 50) + 50)
                .setOndernemend(scores.getOrDefault("ondernemend", 50) + 50)
                .build();

        gebruikerWaardesRepository.save(gebruikerWaardes);
        return gebruikerWaardes;
    }

    private int calculateScore(String antwoord) {
        return switch (antwoord.toLowerCase()) {
            case "eens" -> 10;
            case "oneens" -> -10;
            default -> 0;
        };
    }
}
