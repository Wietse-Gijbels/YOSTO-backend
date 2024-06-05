package com.yosto.yostobackend.gebruiker;

import com.yosto.yostobackend.auth.AuthenticationService;
import com.yosto.yostobackend.email.MailService;
import com.yosto.yostobackend.generic.ServiceException;

import java.io.IOException;
import java.util.*;
import java.util.UUID;

import com.yosto.yostobackend.geschenk.Geschenk;
import com.yosto.yostobackend.geschenk.GeschenkRepository;
import com.yosto.yostobackend.geschenkcategorie.GeschenkCategorie;
import com.yosto.yostobackend.geschenkcategorie.GeschenkCategorieRepository;
import org.springframework.stereotype.Service;

@Service
public class GebruikerService {
    private final GebruikerRepository repository;
    private final AuthenticationService authenticationService;
    private final GeschenkCategorieRepository geschenkCategorieRepository;
    private final MailService emailSenderService;

    public GebruikerService(GebruikerRepository repository, AuthenticationService authenticationService, GeschenkCategorieRepository geschenkCategorieRepository, MailService emailSenderService) {
        this.repository = repository;
        this.authenticationService = authenticationService;
        this.geschenkCategorieRepository = geschenkCategorieRepository;
        this.emailSenderService = emailSenderService;
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
        repository.delete(oudeGebruiker);
        return repository.save(new GebruikerBuilder()
                .setId(oudeGebruiker.getId())
                .setVoornaam(gebruiker.voornaam())
                .setAchternaam(gebruiker.achternaam())
                .setEmail(oudeGebruiker.getEmail())
                .setWoonplaats(gebruiker.woonplaats())
                .setStatus(oudeGebruiker.getStatus())
                .setRol(oudeGebruiker.getRollen().stream().toList().get(0))
                .setLeeftijd(gebruiker.leeftijd())
                .setGeslacht(gebruiker.geslacht())
                .setWachtwoord(oudeGebruiker.getWachtwoord())
                .setGebruikersnaam(oudeGebruiker.getGebruikersnaam())
                .build());
    }


    public void addGeschenkToGebruiker(UUID gebruikerId, UUID geschenkCategorieId) throws IOException {
        Map<String, String> errors = new HashMap<>();
        Optional<Gebruiker> gebruikerOpt = repository.findById(gebruikerId);
        Optional<GeschenkCategorie> geschenkCategorie = geschenkCategorieRepository.findById(geschenkCategorieId);
        Geschenk geschenk;
        if (gebruikerOpt.isPresent() && geschenkCategorie.isPresent()) {
            if (gebruikerOpt.get().getXpAantal() < geschenkCategorie.get().getPrijs()) {
                errors.put("gebruikerGeschenk", "U heeft te weinig xp voor dit geschenk!");
                throw new ServiceException(errors);
            }
            List<Geschenk> geschenken = geschenkCategorieRepository.findAvailableGeschenkenByCategoryId(geschenkCategorieId);
            if (geschenken.isEmpty()) {
                errors.put("gebruikerGeschenk", "Er zijn geen geschenken meer beschikbaar in deze categorie.");
                throw new ServiceException(errors);
            }
            geschenk = geschenken.get(0);
            gebruikerOpt.get().addGeschenk(geschenk,
                    (gebruikerOpt.get().getXpAantal() - geschenkCategorie.get().getPrijs()));
            repository.save(gebruikerOpt.get());

            emailSenderService.sendHtmlEmail(
                    gebruikerOpt.get().getEmail(),
                    "Uw reward ligt klaar in uw mailbox!",
                    geschenkCategorie.get().getNaam(),
                    geschenk.getCode());
        } else {
            errors.put("gebruikerGeschenk", "Gebruiker en/of geschenk niet gevonden.");
            throw new ServiceException(errors);
        }
    }

    public void addXp(UUID id, int xp) {
        Gebruiker gebruiker = repository.findById(id).orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden"));

        // Create a new Gebruiker with the updated XP value
        Gebruiker updatedGebruiker = new GebruikerBuilder()
                .setId(gebruiker.getId())
                .setVoornaam(gebruiker.getVoornaam())
                .setAchternaam(gebruiker.getAchternaam())
                .setEmail(gebruiker.getEmail())
                .setWoonplaats(gebruiker.getWoonplaats())
                .setStatus(gebruiker.getStatus())
                .setRol(gebruiker.getRollen().stream().toList().get(0)) // Assuming only one role
                .setLeeftijd(gebruiker.getLeeftijd())
                .setGeslacht(gebruiker.getGeslacht())
                .setWachtwoord(gebruiker.getWachtwoord())
                .setGebruikersnaam(gebruiker.getGebruikersnaam())
                .setXpAantal(gebruiker.getXpAantal() + xp) // Add the XP
                .setHuidigeStudie(gebruiker.getHuidigeStudie())
                .setBehaaldeDiplomas(new HashSet<>(gebruiker.getBehaaldeDiplomas())) // Ensure a new collection instance
                .build();

        // Delete the old gebruiker
        repository.delete(gebruiker);

        // Save the updated gebruiker back to the repository
        repository.save(updatedGebruiker);
    }
}
