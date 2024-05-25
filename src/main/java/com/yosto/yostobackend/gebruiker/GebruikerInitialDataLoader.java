package com.yosto.yostobackend.gebruiker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
public class GebruikerInitialDataLoader {

    @Bean
    public CommandLineRunner loadData(
            GebruikerRepository gebruikerRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            String email = "helper@helper.helper";
            if (gebruikerRepository.findByEmail(email).isEmpty()) {
                Gebruiker gebruiker1 = GebruikerBuilder
                        .gebruikerBuilder()
                        .setId(UUID.randomUUID())
                        .setVoornaam("helper")
                        .setAchternaam("helper")
                        .setGebruikersnaam("helper")
                        .setEmail(email)
                        .setWachtwoord(passwordEncoder.encode("yosto"))
                        .setGeslacht("helper")
                        .setLeeftijd(25)
                        .setWoonplaats("helper")
                        .setRol(Rol.STUDYHELPER)
                        .setStatus(Status.ONLINE)
                        .setXpAantal(300)
                        .build();

                gebruikerRepository.save(gebruiker1);
            }
            email = "looker@looker.looker";
            if (gebruikerRepository.findByEmail(email).isEmpty()) {
                Gebruiker gebruiker2 = GebruikerBuilder
                        .gebruikerBuilder()
                        .setId(UUID.randomUUID())
                        .setVoornaam("looker")
                        .setAchternaam("looker")
                        .setGebruikersnaam("looker")
                        .setEmail(email)
                        .setWachtwoord(passwordEncoder.encode("yosto"))
                        .setGeslacht("looker")
                        .setLeeftijd(25)
                        .setWoonplaats("looker")
                        .setRol(Rol.STUDYLOOKER)
                        .setStatus(Status.ONLINE)
                        .build();

                gebruikerRepository.save(gebruiker2);
            }
        };
    }
}
