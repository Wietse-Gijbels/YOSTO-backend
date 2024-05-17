package com.yosto.yostobackend.auth;

import com.yosto.yostobackend.config.JwtService;
import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.gebruiker.GebruikerBuilder;
import com.yosto.yostobackend.gebruiker.GebruikerRepository;
import com.yosto.yostobackend.gebruiker.Status;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .setStatus(Status.ONLINE)
                .build();
        repository.save(gebruiker);
        String jwtToken = jwtService.generateToken(gebruiker);
        return AuthenticationResponseBuilder.authenticationResponseBuilder()
                .setToken(gebruiker.getId().toString())
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
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
