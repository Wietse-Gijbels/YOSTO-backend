package com.yosto.yostobackend.auth;

import com.yosto.yostobackend.config.JwtService;
import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.gebruiker.GebruikerBuilder;
import com.yosto.yostobackend.gebruiker.GebruikerRepository;
import com.yosto.yostobackend.gebruiker.Status;
import com.yosto.yostobackend.generic.ServiceException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.yosto.yostobackend.studierichting.Studierichting;
import com.yosto.yostobackend.studierichting.StudierichtingService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
  private final GebruikerRepository repository;

  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;

  private final StudierichtingService studierichtingService;

  public AuthenticationService(
          GebruikerRepository repository,
          PasswordEncoder passwordEncoder,
          JwtService jwtService,
          AuthenticationManager authenticationManager, StudierichtingService studierichtingService
  ) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
      this.studierichtingService = studierichtingService;
  }

  public AuthenticationResponse registreer(RegisterRequest request) {
    Map<String, String> errors = new HashMap<>();

    // Eerst checken of de gebruiker al bestaat
    Optional<Gebruiker> gebruikerDuplicate = repository.findByEmail(
      request.getEmail().toLowerCase()
    );
    // Direct error throwen als gebruiker bestaat
    if (gebruikerDuplicate.isPresent()) {
      errors.put("errorDuplicate", "Er is al een account met dit e-mailadres.");
      throw new ServiceException(errors);
    }
    // Overige checks controlleren
    if (request.getVoornaam() == null || request.getVoornaam().isBlank()) {
      errors.put("errorVoornaam", "Voornaam is verplicht.");
    }
    if (request.getAchternaam() == null || request.getAchternaam().isBlank()) {
      errors.put("errorAchternaam", "Achternaam is verplicht.");
    }
    //        if (request.getGebruikersnaam() == null || request.getGebruikersnaam().isBlank()) {
    //            errors.put("errorGebruikersnaam", "Gebruikersnaam is verplicht.");
    //        }
    if (request.getEmail() == null || request.getEmail().isBlank()) {
      errors.put("errorEmail", "Email is verplicht.");
    }
    if (request.getWachtwoord() == null || request.getWachtwoord().isBlank()) {
      errors.put("errorWachtwoord", "Wachtwoord is verplicht.");
    }
    if (request.getWoonplaats() == null || request.getWoonplaats().isBlank()) {
      errors.put("errorProvincie", "Provincie is verplicht.");
    }
    //        if (request.getRol() == null) {
    //            errors.put("errorRol", "Rol is verplicht.");
    //        }
    //        if (request.getRol() == Rol.ADMIN) {
    //            errors.put("errorAdmin", "U heeft geen rechten om een admin account aan te maken.");
    //        }

    parseStudierichting(request.getHuidigeStudieAndNiveau(), errors);

    if (errors.containsKey("errorRichtingParser")) {
      throw new ServiceException(errors);
    }

    String naam = errors.get("naam");
    String niveau = errors.get("niveau");
    if (naam == null || niveau == null) {
      errors.put("errorRichtingParser", "Kies een richting uit de lijst!");
      throw new ServiceException(errors);
    }

    if (!errors.isEmpty()) {
      throw new ServiceException(errors);
    }

    Studierichting huidigeStudie = studierichtingService.findByNaamAndNiveauNaam(naam, niveau);

    Gebruiker gebruiker = GebruikerBuilder
      .gebruikerBuilder()
            .setVoornaam(request.getVoornaam())
            .setAchternaam(request.getAchternaam())
            .setGebruikersnaam(request.getGebruikersnaam())
            .setEmail(request.getEmail().toLowerCase())
            .setGeslacht(request.getGeslacht())
            .setLeeftijd(request.getLeeftijd())
            .setWoonplaats(request.getWoonplaats())
            .setWachtwoord(passwordEncoder.encode(request.getWachtwoord()))
            .setRol(request.getRol())
            .setStatus(Status.ONLINE)
            .setXpAantal(0)
            .setHuidigeStudie(huidigeStudie)
      .build();
    repository.save(gebruiker);
    String jwtToken = jwtService.generateToken(gebruiker);
    return AuthenticationResponseBuilder
      .authenticationResponseBuilder()
      .setToken(jwtToken)
      .build();
  }

  public AuthenticationResponse login(AuthenticationRequest request) {
    Map<String, String> errors = new HashMap<>();

    if (request.getEmail() == null || request.getEmail().isBlank()) {
      errors.put("errorLoginEmail", "Email is verplicht!");
    }
    if (request.getWachtwoord() == null || request.getWachtwoord().isBlank()) {
      errors.put("errorLoginWachtwoord", "Wachtwoord is verplicht!");
    }

    if (!errors.isEmpty()) {
      throw new ServiceException(errors);
    }

    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          request.getEmail(),
          request.getWachtwoord()
        )
      );
    } catch (AuthenticationException e) {
      errors.put("errorLogin", "Ongeldig e-mailadres en/of wachtwoord!");
      throw new ServiceException(errors);
    }

    Gebruiker gebruiker = repository.findByEmail(request.getEmail()).orElseThrow();
    String jwtToken = jwtService.generateToken(gebruiker);
    return AuthenticationResponseBuilder
      .authenticationResponseBuilder()
      .setToken(jwtToken)
      .build();
  }

  private void parseStudierichting(String input, Map<String, String> errors) {
    int index = input.lastIndexOf('(');
    if (index == -1 || !input.endsWith(")")) {
      errors.put("errorRichtingParser", "Kies een richting uit de lijst!");
      return;
    }

    String naam = input.substring(0, index).trim();
    String niveau = input.substring(index + 1, input.length() - 1).trim();

    errors.put("naam", naam);
    errors.put("niveau", niveau);
  }

}
