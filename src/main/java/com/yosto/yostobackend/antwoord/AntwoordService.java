package com.yosto.yostobackend.antwoord;

import com.yosto.yostobackend.config.JwtService;
import com.yosto.yostobackend.gebruiker.GebruikerService;
import com.yosto.yostobackend.generic.ServiceException;
import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.gebruiker.GebruikerRepository;
import com.yosto.yostobackend.vraag.Vraag;
import com.yosto.yostobackend.vraag.VraagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.HashMap;

@Service
public class AntwoordService {

    private final AntwoordRepository antwoordRepository;

    private final GebruikerService gebruikerService;

    private final VraagRepository vraagRepository;

    private final JwtService jwtService;

    public AntwoordService(AntwoordRepository antwoordRepository, GebruikerService gebruikerService, VraagRepository vraagRepository, JwtService jwtService) {
        this.antwoordRepository = antwoordRepository;
        this.gebruikerService = gebruikerService;
        this.vraagRepository = vraagRepository;
        this.jwtService = jwtService;
    }

    public List<Antwoord> saveAntwoorden(String token, List<AntwoordDTO> antwoordDTOs) {
        Map<String, String> errors = new HashMap<>();
        Gebruiker gebruiker = gebruikerService.getGebruikerByEmail(jwtService.extractEmail(token));

        List<Antwoord> antwoorden = antwoordDTOs.stream().map(dto -> {
            Vraag vraag = vraagRepository.findById(dto.getVraagId()).orElseThrow(() -> {
                errors.put("errorFindVraagById", "Er bestaat geen vraag met deze id.");
                throw new ServiceException(errors);
            });

            return AntwoordBuilder.antwoordBuilder()
                    .setGebruiker(gebruiker)
                    .setVraag(vraag)
                    .setAntwoord(dto.getAntwoord())
                    .build();
        }).collect(Collectors.toList());

        return antwoordRepository.saveAll(antwoorden);
    }

    public List<Antwoord> getAntwoordenByGebruiker(Gebruiker gebruiker) {
        return antwoordRepository.findByGebruiker(gebruiker);
    }

    public int getAmountOfAntwoorden(Gebruiker gebruiker) {
        return antwoordRepository.findByGebruiker(gebruiker).size();
    }
}
