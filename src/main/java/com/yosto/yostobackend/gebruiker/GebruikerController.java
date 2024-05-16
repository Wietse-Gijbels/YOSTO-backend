package com.yosto.yostobackend.gebruiker;

import com.yosto.yostobackend.auth.AuthenticationRequest;
import com.yosto.yostobackend.auth.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @MessageMapping("/gebruiker.disconnectGebruiker")
    @SendTo("/gebruiker/public")
    public Gebruiker disconnectGebruiker(@Payload Gebruiker gebruiker) {
        gebruikerService.disconnect(gebruiker);
        return gebruiker;
    }

    @GetMapping("/online")
    public List<Gebruiker> getConnectedGebruikers() {
        return gebruikerService.findConnectedGebruikers();
    }
}
