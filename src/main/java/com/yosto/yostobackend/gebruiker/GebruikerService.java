package com.yosto.yostobackend.gebruiker;

import com.yosto.yostobackend.email.EmailSenderService;
import com.yosto.yostobackend.generic.ServiceException;

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
  private final GeschenkRepository geschenkRepository;
  private final GeschenkCategorieRepository geschenkCategorieRepository;
  private final EmailSenderService emailSenderService;


    public GebruikerService(GebruikerRepository repository, GeschenkRepository geschenkRepository, GeschenkCategorieRepository geschenkCategorieRepository, EmailSenderService emailSenderService) {
    this.repository = repository;
        this.geschenkRepository = geschenkRepository;
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

    public void addGeschenkToGebruiker(UUID gebruikerId, UUID geschenkCategorieId) {
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
            emailSenderService.sendEmail(
                    gebruikerOpt.get().getEmail(),
                    "Uw reward ligt klaar in uw mailbox!",
                    "De code voor uw gekozen geschenk " + geschenkCategorie.get().getNaam() + " is : " + geschenk.getCode() +
                            " \n Geniet ervan en blijf doorsparen voor uw volgende geschenken! \n\n Groetjes,\n Het Yosto-team" );

        } else {
            errors.put("gebruikerGeschenk", "Gebruiker en/of geschenk niet gevonden.");
            throw new ServiceException(errors);
        }
    }
}
