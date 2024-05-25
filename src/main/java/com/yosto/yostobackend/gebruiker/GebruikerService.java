package com.yosto.yostobackend.gebruiker;

import com.yosto.yostobackend.generic.ServiceException;

import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;

import com.yosto.yostobackend.geschenk.Geschenk;
import com.yosto.yostobackend.geschenk.GeschenkRepository;
import com.yosto.yostobackend.geschenkcategorie.GeschenkCategorie;
import com.yosto.yostobackend.geschenkcategorie.GeschenkCategorieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

@Service
public class GebruikerService {
  private final GebruikerRepository repository;
  private final GeschenkRepository geschenkRepository;
  private final GeschenkCategorieRepository geschenkCategorieRepository;


    public GebruikerService(GebruikerRepository repository, GeschenkRepository geschenkRepository, GeschenkCategorieRepository geschenkCategorieRepository) {
    this.repository = repository;
        this.geschenkRepository = geschenkRepository;
        this.geschenkCategorieRepository = geschenkCategorieRepository;
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
            gebruikerOpt.get().addGeschenk(geschenken.get(0),
                    (gebruikerOpt.get().getXpAantal() - geschenkCategorie.get().getPrijs()));
            repository.save(gebruikerOpt.get());
        } else {
            errors.put("gebruikerGeschenk", "Gebruiker en/of geschenk niet gevonden.");
            throw new ServiceException(errors);
        }
    }
}
