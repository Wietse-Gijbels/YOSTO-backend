package com.yosto.yostobackend.gebruiker;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/id/{id}")
    public Gebruiker getNaamFromToken(@PathVariable String id) {
        return gebruikerService.getById(UUID.fromString(id));
    }
}
