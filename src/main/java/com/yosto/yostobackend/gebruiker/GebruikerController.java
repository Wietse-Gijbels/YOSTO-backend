package com.yosto.yostobackend.gebruiker;

import com.yosto.yostobackend.config.JwtService;
import com.yosto.yostobackend.generic.ServiceException;

import java.util.*;

import com.yosto.yostobackend.lookerQueue.LookerQueue;
import com.yosto.yostobackend.lookerQueue.LookerQueueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/gebruiker")
public class GebruikerController {
  private final GebruikerService gebruikerService;

  private final LookerQueueService lookerQueueService;

  private final JwtService jwtService;

  public GebruikerController(GebruikerService gebruikerService, LookerQueueService lookerQueueService, JwtService jwtService) {
    this.gebruikerService = gebruikerService;
    this.lookerQueueService = lookerQueueService;
    this.jwtService = jwtService;
  }

  @GetMapping("/{token}")
  public Gebruiker getGebruiker(@PathVariable String token) {
    return gebruikerService.getGebruikerByEmail(jwtService.extractEmail(token));
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

  @GetMapping("/email/{token}")
  public String getEmailFromToken(@PathVariable String token) {
    return jwtService.extractEmail(token);
  }

  @GetMapping("/id/{token}")
  public ResponseEntity<Map<String, String>> getIdFromToken(@PathVariable String token) {
    Gebruiker gebruiker = gebruikerService.getGebruikerByEmail(jwtService.extractEmail(token));
    Map<String, String> response = new HashMap<>();
    response.put("id", gebruiker.getId().toString());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/gebruiker/{id}")
    public Gebruiker getGebruikerById(@PathVariable UUID id) {
        return gebruikerService.getGebruikerById(id);
    }

  @PutMapping("/{token}")
    public Gebruiker updateGebruiker(
        @PathVariable String token,
        @RequestBody UpdateGebruikerDTO gebruiker
    ) {
        return gebruikerService.updateGebruiker(
        jwtService.extractEmail(token),
        gebruiker
        );
    }

  @PostMapping("/xp")
  public ResponseEntity<Void> addXp(@RequestBody Map<String, Object> request) {
    UUID id = UUID.fromString((String) request.get("id"));
    int xp = (int) request.get("xp");
    gebruikerService.addXp(id, xp);
    return ResponseEntity.ok().build();
  }


  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(
    {
      MethodArgumentNotValidException.class,
      ServiceException.class,
      ResponseStatusException.class
    }
  )
  public Map<String, String> handleValidationExceptions(Exception ex) {
    Map<String, String> errors = new HashMap<>();
    if (ex instanceof MethodArgumentNotValidException) {
      ((MethodArgumentNotValidException) ex).getBindingResult()
        .getAllErrors()
        .forEach(
          error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put("error", fieldName + ": " + errorMessage);
          }
        );
    } else if (ex instanceof ServiceException) {
      errors.putAll(((ServiceException) ex).getErrors());
    } else {
      errors.put("error", ex.getMessage());
    }
    return errors;
  }
}
