package com.yosto.yostobackend.gebruiker;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GebruikersController {

    private final GebruikerService service;

    public GebruikersController(GebruikerService service) {
        this.service = service;
    }

    @MessageMapping("/gebruiker.disconnectGebruiker")
    @SendTo("/gebruiker/public")
    public Gebruiker disconnectGebruiker(@Payload Gebruiker gebruiker) {
        service.disconnect(gebruiker);
        return gebruiker;
    }

    @GetMapping("/gebruikers")
    public ResponseEntity<List<Gebruiker>> getConnectedGebruikers() {
        return ResponseEntity.ok(service.findConnectedGebruikers());
    }

}
