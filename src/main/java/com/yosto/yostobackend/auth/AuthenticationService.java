package com.yosto.yostobackend.auth;

import com.yosto.yostobackend.config.JwtService;
import com.yosto.yostobackend.email.MailService;
import com.yosto.yostobackend.gebruiker.*;
import com.yosto.yostobackend.generic.ServiceException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


import java.util.*;

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

    private final MailService mailService;
    private final GebruikerRepository gebruikerRepository;


    public AuthenticationService(
            GebruikerRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager, StudierichtingService studierichtingService, MailService mailService,
            GebruikerRepository gebruikerRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.studierichtingService = studierichtingService;
        this.mailService = mailService;
        this.gebruikerRepository = gebruikerRepository;
    }

    public AuthenticationResponse registreer(RegisterRequest request) throws IOException {
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

        parseStudierichting(request.getHuidigeStudieAndNiveau(), errors);

        // if (errors.containsKey("errorRichtingParser")) {
        //  throw new ServiceException(errors);
        // }

        String naam = errors.get("naam");
        String niveau = errors.get("niveau");
        if (naam == null || niveau == null) {
            errors.put("errorRichtingParser", "Kies een richting uit de lijst!");
            // throw new ServiceException(errors);
        }

        Studierichting huidigeStudie;

        try {
            huidigeStudie = studierichtingService.findByNaamAndNiveauNaam(naam, niveau);
        } catch (ServiceException e) {
            huidigeStudie = new Studierichting();
            errors.put("errorRichtingParser", e.getMessage().replace("[", "").replace("]", ""));
        }

        Set<Studierichting> behaaldeDiplomas = new HashSet<>();
        // Studierichting huidigeStudie = studierichtingService.findByNaamAndNiveauNaam(naam, niveau);
        if (request.getBehaaldeDiplomas() != null) {
            for (String diploma : request.getBehaaldeDiplomas()) {
                Map<String, String> parseErrors = new HashMap<>();
                if (diploma.isBlank()) {
                    break;
                }
                parseStudierichting(diploma, parseErrors);
                if (parseErrors.containsKey("naam") && parseErrors.containsKey("niveau")) {
                    String diplomaNaam = parseErrors.get("naam");
                    String diplomaNiveau = parseErrors.get("niveau");
                    Studierichting behaaldeStudie;
                    try {
                        behaaldeStudie = studierichtingService.findByNaamAndNiveauNaam(diplomaNaam, diplomaNiveau);
                    } catch (ServiceException e) {
                        errors.put("errorDiplomaParser", "Kies enkel richtingen uit de lijst!");
                        behaaldeStudie = null;
                    }
                    if (behaaldeStudie != null) {
                        behaaldeDiplomas.add(behaaldeStudie);
                    }
                } else {
                    errors.put("errorDiplomaParser", "Kies enkel richtingen uit de lijst!");
                    // throw new ServiceException(errors);
                }
            }
        }

        if (!errors.isEmpty()) {
            errors.remove("naam");
            errors.remove("niveau");
            if (!errors.isEmpty()) {
                throw new ServiceException(errors);
            }
        }

        String verificatieCode = UUID.randomUUID().toString();

        Gebruiker gebruiker = new GebruikerBuilder()
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
                .setBehaaldeDiplomas(behaaldeDiplomas)
                .setActieveRol(request.getRol().stream().findFirst().orElse(null))
                .build();
        gebruiker.setVerificatieCode(verificatieCode);
        repository.save(gebruiker);
        mailService.sendTextEmail(gebruiker.getEmail(), "Uw verificatiecode", "Uw verificatiecode is: " + verificatieCode);

        String jwtToken = jwtService.generateToken(gebruiker);
        return AuthenticationResponseBuilder
                .authenticationResponseBuilder()
                .setToken(jwtToken)
                .setRol(gebruiker.getActieveRol())
                .build();
    }

    public void verifyAccount(String email, String code) {
        Gebruiker gebruiker = repository.findByEmail(email).orElseThrow(() -> new ServiceException(Map.of("errorEmail", "Gebruiker niet gevonden")));
        if (gebruiker.getVerificatieCode().equals(code)) {
            gebruiker.setAccountActief(true);
            gebruiker.setVerificatieCode(null);
            repository.save(gebruiker);
        } else {
            throw new ServiceException(Map.of("errorVerify", "Verificatiecode is onjuist"));
        }
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            errors.put("errorLoginEmail", "Email is verplicht!");
        }
        if (request.getWachtwoord() == null || request.getWachtwoord().isBlank()) {
            errors.put("errorLoginWachtwoord", "Wachtwoord is verplicht!");
        }

        if (gebruikerRepository.findByEmail(request.getEmail()).isPresent()) {
            if (!gebruikerRepository.findByEmail(request.getEmail()).get().isAccountActief()) {
                errors.put("errorActiefAccount", "Uw moet eerst uw account verifiëren!");
            }
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
                .setRol(gebruiker.getActieveRol())
                .build();
    }

    public void parseStudierichting(String input, Map<String, String> errors) {
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

    public void switchRol(String token) {
        Gebruiker oudeGebruiker = gebruikerRepository.findByEmail(jwtService.extractEmail(token)).orElseThrow();
        gebruikerRepository.delete(oudeGebruiker);
        Gebruiker newGebruiker = new GebruikerBuilder()
                .setVoornaam(oudeGebruiker.getVoornaam())
                .setAchternaam(oudeGebruiker.getAchternaam())
                .setEmail(oudeGebruiker.getEmail())
                .setWoonplaats(oudeGebruiker.getWoonplaats())
                .setStatus(oudeGebruiker.getStatus())
                .setRol(oudeGebruiker.getRollen())
                .setLeeftijd(oudeGebruiker.getLeeftijd())
                .setGeslacht(oudeGebruiker.getGeslacht())
                .setWachtwoord(oudeGebruiker.getWachtwoord())
                .setGebruikersnaam(oudeGebruiker.getGebruikersnaam())
                .setActieveRol(oudeGebruiker.getActieveRol().equals(Rol.STUDYHELPER) ? Rol.STUDYLOOKER : Rol.STUDYHELPER)
                .setXpAantal(oudeGebruiker.getXpAantal())
                .build();
        newGebruiker.setAccountActief();
        gebruikerRepository.save(newGebruiker);

    }
}
