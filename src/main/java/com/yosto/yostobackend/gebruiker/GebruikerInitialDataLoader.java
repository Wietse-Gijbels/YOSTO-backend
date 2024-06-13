package com.yosto.yostobackend.gebruiker;

import com.yosto.yostobackend.geschenk.Geschenk;
import com.yosto.yostobackend.geschenk.GeschenkBuilder;
import com.yosto.yostobackend.geschenk.GeschenkRepository;
import com.yosto.yostobackend.geschenkcategorie.GeschenkCategorie;
import com.yosto.yostobackend.geschenkcategorie.GeschenkCategorieBuilder;
import com.yosto.yostobackend.geschenkcategorie.GeschenkCategorieRepository;
import com.yosto.yostobackend.lookerQueue.LookerQueueService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.UUID;

@Configuration
public class GebruikerInitialDataLoader {

    @Bean
    public CommandLineRunner loadData(
            GebruikerRepository gebruikerRepository,
            PasswordEncoder passwordEncoder,
            GeschenkCategorieRepository geschenkCategorieRepository,
            GeschenkRepository geschenkRepository,
            LookerQueueService lookerQueueService
    ) {
        return args -> {
            String email = "kuno.vercammen@gmail.com";
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
                        .setRol(Set.of(Rol.STUDYHELPER,Rol.STUDYLOOKER))
                        .setStatus(Status.ONLINE)
                        .setXpAantal(300)
                        .setActieveRol(Rol.STUDYHELPER)
                        .build();
                gebruiker1.setVerificatieCode("code");
                gebruiker1.setAccountActief();
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
                        .setRol(Set.of(Rol.STUDYLOOKER))
                        .setStatus(Status.ONLINE)
                        .setActieveRol(Rol.STUDYLOOKER)
                        .build();
                gebruiker2.setVerificatieCode("code");
                gebruiker2.setAccountActief();
                gebruikerRepository.save(gebruiker2);
            }

            String categorieNaam = "Filmticket";
            if (geschenkCategorieRepository.findByNaam(categorieNaam).isEmpty()) {
                GeschenkCategorie categorie = new GeschenkCategorieBuilder()
                        .setNaam(categorieNaam)
                        .setPrijs(100)
                        .setBeschrijving("Dit is een filmticket")
                        .setFotoUrl("8cf0a46a-c39f-4272-8c76-03005266d146_Kinepolis.png")
                        .build();

                geschenkCategorieRepository.save(categorie);

                // Geschenken toevoegen aan de categorie
                Geschenk geschenk1 = new GeschenkBuilder()
                        .setTitel("Ticket 1023")
                        .setCode("1923-2931-2983")
                        .setBeschikbaar(true)
                        .setGeschenkCategorie(categorie)
                        .build();

                Geschenk geschenk2 = new GeschenkBuilder()
                        .setTitel("Ticket 2045")
                        .setCode("2045-3921-2983")
                        .setBeschikbaar(true)
                        .setGeschenkCategorie(categorie)
                        .build();

                geschenkRepository.save(geschenk1);
                geschenkRepository.save(geschenk2);
            }
            categorieNaam = "Walibi Ticket";
            if (geschenkCategorieRepository.findByNaam(categorieNaam).isEmpty()) {
                GeschenkCategorie categorie = new GeschenkCategorieBuilder()
                        .setNaam(categorieNaam)
                        .setPrijs(100)
                        .setBeschrijving("Dit is een Walibi Ticket")
                        .setFotoUrl("walibi.jpeg")
                        .build();

                geschenkCategorieRepository.save(categorie);

                // Geschenken toevoegen aan de categorie
                Geschenk geschenk1 = new GeschenkBuilder()
                        .setTitel("Ticket 1")
                        .setCode("1923-29A1-2983")
                        .setBeschikbaar(true)
                        .setGeschenkCategorie(categorie)
                        .build();

                Geschenk geschenk2 = new GeschenkBuilder()
                        .setTitel("Ticket 5")
                        .setCode("2045-3921-2983")
                        .setBeschikbaar(true)
                        .setGeschenkCategorie(categorie)
                        .build();

                geschenkRepository.save(geschenk1);
                geschenkRepository.save(geschenk2);
            }
            categorieNaam = "€10 McDonalds Voucher";
            if (geschenkCategorieRepository.findByNaam(categorieNaam).isEmpty()) {
                GeschenkCategorie categorie = new GeschenkCategorieBuilder()
                        .setNaam(categorieNaam)
                        .setPrijs(100)
                        .setBeschrijving("Dit is een voucher voor 10 euro eetplezier!")
                        .setFotoUrl("mcdonalds.png")
                        .build();

                geschenkCategorieRepository.save(categorie);

                // Geschenken toevoegen aan de categorie
                Geschenk geschenk1 = new GeschenkBuilder()
                        .setTitel("Ticket 103")
                        .setCode("1923-2931-2983")
                        .setBeschikbaar(true)
                        .setGeschenkCategorie(categorie)
                        .build();

                Geschenk geschenk2 = new GeschenkBuilder()
                        .setTitel("Ticket 045")
                        .setCode("2045-3921-2983")
                        .setBeschikbaar(true)
                        .setGeschenkCategorie(categorie)
                        .build();

                geschenkRepository.save(geschenk1);
                geschenkRepository.save(geschenk2);
            }
            categorieNaam = "€20 Game Mania Gift Card";
            if (geschenkCategorieRepository.findByNaam(categorieNaam).isEmpty()) {
                GeschenkCategorie categorie = new GeschenkCategorieBuilder()
                        .setNaam(categorieNaam)
                        .setPrijs(100)
                        .setBeschrijving("Dit is een gift card voor de Game Mania van 20 euro!")
                        .setFotoUrl("gamemania.jpeg")
                        .build();

                geschenkCategorieRepository.save(categorie);

                // Geschenken toevoegen aan de categorie
                Geschenk geschenk1 = new GeschenkBuilder()
                        .setTitel("Ticket 23")
                        .setCode("1923-2931-2983")
                        .setBeschikbaar(true)
                        .setGeschenkCategorie(categorie)
                        .build();

                Geschenk geschenk2 = new GeschenkBuilder()
                        .setTitel("Ticket 204125")
                        .setCode("2045-3921-2983")
                        .setBeschikbaar(true)
                        .setGeschenkCategorie(categorie)
                        .build();

                geschenkRepository.save(geschenk1);
                geschenkRepository.save(geschenk2);
            }
        };
    }
}
