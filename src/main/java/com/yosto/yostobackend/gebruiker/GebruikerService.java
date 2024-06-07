package com.yosto.yostobackend.gebruiker;

import com.yosto.yostobackend.auth.AuthenticationService;
import com.yosto.yostobackend.email.MailService;
import com.yosto.yostobackend.generic.ServiceException;
import com.yosto.yostobackend.geschenk.Geschenk;
import com.yosto.yostobackend.geschenkcategorie.GeschenkCategorie;
import com.yosto.yostobackend.geschenkcategorie.GeschenkCategorieRepository;
import com.yosto.yostobackend.notifications.FCMService;
import com.yosto.yostobackend.notifications.NotificationRequest;
import com.yosto.yostobackend.notifications.NotificationService;
import com.yosto.yostobackend.studierichting.Studierichting;
import com.yosto.yostobackend.studierichting.StudierichtingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class GebruikerService {
    private final GebruikerRepository repository;
    private final AuthenticationService authenticationService;
    private final GeschenkCategorieRepository geschenkCategorieRepository;
    private final MailService emailSenderService;
    private final StudierichtingService studierichtingService;
    private final GebruikerRepository gebruikerRepository;
    private final FCMService notificationService;


    public GebruikerService(GebruikerRepository repository, AuthenticationService authenticationService, GeschenkCategorieRepository geschenkCategorieRepository, MailService emailSenderService, StudierichtingService studierichtingService, GebruikerRepository gebruikerRepository, FCMService notificationService) {
        this.repository = repository;
        this.authenticationService = authenticationService;
        this.geschenkCategorieRepository = geschenkCategorieRepository;
        this.emailSenderService = emailSenderService;
        this.studierichtingService = studierichtingService;
        this.gebruikerRepository = gebruikerRepository;
        this.notificationService = notificationService;
    }

    public void disconnect(Gebruiker gebruiker) {
        repository.findById(gebruiker.getId()).orElseThrow();
        gebruiker.disconnect();
        repository.save(gebruiker);
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
                .setRol(oudeGebruiker.getRollen())
                .setLeeftijd(gebruiker.leeftijd())
                .setGeslacht(gebruiker.geslacht())
                .setWachtwoord(oudeGebruiker.getWachtwoord())
                .setGebruikersnaam(oudeGebruiker.getGebruikersnaam())
                        .setActieveRol(oudeGebruiker.getActieveRol())
                .build());
    }


    public void addGeschenkToGebruiker(UUID gebruikerId, UUID geschenkCategorieId) throws IOException, ExecutionException, InterruptedException {
        Map<String, String> errors = new HashMap<>();
        Optional<Gebruiker> gebruikerOpt = repository.findById(gebruikerId);
        NotificationRequest n = new NotificationRequest();
        n.setBody("PLSSS WERK");
        n.setTitle("PLSSS WERK");
        n.setToken(gebruikerOpt.get().getFcmToken());
        notificationService.sendMessageToToken(n);

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

    public Rol getRoleByEmail(String email) {
        return getGebruikerByEmail(email).getActieveRol();
    }

    public Set<Rol> getRollen(String email) {
        return getGebruikerByEmail(email).getRollen();
    }

    public Gebruiker updateRole(Rol rol, String email) {
        Gebruiker gebruiker = getGebruikerByEmail(email);
        repository.delete(gebruiker);
        return repository.save(new GebruikerBuilder()
                .setId(gebruiker.getId())
                .setVoornaam(gebruiker.getVoornaam())
                .setAchternaam(gebruiker.getAchternaam())
                .setEmail(gebruiker.getEmail())
                .setWoonplaats(gebruiker.getWoonplaats())
                .setStatus(gebruiker.getStatus())
                .setRol(gebruiker.getRollen())
                .setLeeftijd(gebruiker.getLeeftijd())
                .setGeslacht(gebruiker.getGeslacht())
                .setWachtwoord(gebruiker.getWachtwoord())
                .setGebruikersnaam(gebruiker.getGebruikersnaam())
                .setActieveRol(rol)
                .build());
    }
    public void addFavorieteStudierichting(Gebruiker gebruiker, UUID studierichtingId) {
        Studierichting studierichting = studierichtingService.findStudierichtingById(studierichtingId);
        gebruiker.addFavorieteStudierichting(studierichting);
        repository.save(gebruiker);
    }

    public void removeFavorieteStudierichting(Gebruiker gebruiker, UUID studierichtingId) {
        Studierichting studierichting = studierichtingService.findStudierichtingById(studierichtingId);
        gebruiker.removeFavorieteStudierichting(studierichting);
        repository.save(gebruiker);
    }

    public Page<Studierichting> findAllFavorieteStudierichtingen(Gebruiker gebruiker, int page, int size) {
        List<Studierichting> favorieteStudierichtingen = gebruiker.getFavorieteStudierichtingen();

        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), favorieteStudierichtingen.size());

        List<Studierichting> pagedStudierichtingen = favorieteStudierichtingen.subList(start, end);

        return new PageImpl<>(pagedStudierichtingen, pageable, favorieteStudierichtingen.size());
    }

    public List<Gebruiker> findConnectedGebruikers() {
        return repository.findAllByStatus(Status.ONLINE);
    }
}
