package com.yosto.yostobackend.auth;

import com.yosto.yostobackend.config.JwtService;
import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.gebruiker.GebruikerBuilder;
import com.yosto.yostobackend.gebruiker.GebruikerRepository;
import com.yosto.yostobackend.gebruiker.Rol;
import com.yosto.yostobackend.generic.ServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private final GebruikerRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(GebruikerRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse registreer(RegisterRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (request.getVoornaam() == null || request.getVoornaam().isBlank()) {
            errors.put("errorVoornaam", "Voornaam is verplicht.");
        }
        if (request.getAchternaam() == null || request.getAchternaam().isBlank()) {
            errors.put("errorAchternaam", "Achternaam is verplicht.");
        }
        if (request.getGebruikersnaam() == null || request.getGebruikersnaam().isBlank()) {
            errors.put("errorGebruikersnaam", "Gebruikersnaam is verplicht.");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            errors.put("errorEmail", "Email is verplicht.");
        }
        if (request.getWachtwoord() == null || request.getWachtwoord().isBlank()) {
            errors.put("errorWachtwoord", "Wachtwoord is verplicht.");
        }
        if (request.getWoonplaats() == null || request.getWoonplaats().isBlank()) {
            errors.put("errorProvincie", "Provincie is verplicht.");
        }
        if (request.getRol() == null) {
            errors.put("errorRol", "Rol is verplicht.");
        }
        if (request.getRol() == Rol.ADMIN) {
            errors.put("errorAdmin", "U heeft geen rechten om een admin account aan te maken.");
        }

        if (!errors.isEmpty()) {
            throw new ServiceException(errors);
        }

        Gebruiker gebruiker = GebruikerBuilder
                .gebruikerBuilder()
                .setVoornaam(request.getVoornaam())
                .setAchternaam(request.getAchternaam())
                .setGebruikersnaam(request.getGebruikersnaam())
                .setEmail(request.getEmail())
                .setGeslacht(request.getGeslacht())
                .setLeeftijd(request.getLeeftijd())
                .setWoonplaats(request.getWoonplaats())
                .setWachtwoord(passwordEncoder.encode(request.getWachtwoord()))
                .setRol(request.getRol())
                .build();
        repository.save(gebruiker);
        String jwtToken = jwtService.generateToken(gebruiker);
        return AuthenticationResponseBuilder.authenticationResponseBuilder()
                .setToken(jwtToken)
                .build();
    }


    public AuthenticationResponse login(AuthenticationRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            errors.put("errorEmail", "Email is verplicht.");
        }
        if (request.getWachtwoord() == null || request.getWachtwoord().isBlank()) {
            errors.put("errorWachtwoord", "Wachtwoord is verplicht.");
        }

        if (!errors.isEmpty()) {
            throw new ServiceException(errors);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getWachtwoord()
                )
        );
        Gebruiker gebruiker = repository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(gebruiker);
        return AuthenticationResponseBuilder.authenticationResponseBuilder()
                .setToken(jwtToken)
                .build();
    }

}
