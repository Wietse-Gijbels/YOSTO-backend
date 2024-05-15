package com.yosto.yostobackend.gebruiker;

import com.yosto.yostobackend.auth.AuthenticationRequest;
import com.yosto.yostobackend.auth.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gebruiker")
public class GebruikerController {

    private final GebruikerService gebruikerService;

    public GebruikerController(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }

    @GetMapping("/{email}")
    public Gebruiker getGebruiker(
            @PathVariable String email
    ) {
        return gebruikerService.getGebruikerByEmail(email);
    }
}
