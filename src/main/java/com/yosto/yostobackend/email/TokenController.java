package com.yosto.yostobackend.email;

import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.gebruiker.GebruikerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private GebruikerRepository gebruikerRepository;

    @PostMapping("/api/v1/registerToken")
    public void registerToken(@RequestBody TokenRequest request) {
        Gebruiker gebruiker = gebruikerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden"));
        gebruiker.setFcmToken(request.getToken());
        gebruikerRepository.save(gebruiker);
    }
}

