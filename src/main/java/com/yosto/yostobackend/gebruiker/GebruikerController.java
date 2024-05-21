package com.yosto.yostobackend.gebruiker;

import com.yosto.yostobackend.config.JwtService;
import com.yosto.yostobackend.generic.ServiceException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

  private final JwtService jwtService;

  public GebruikerController(GebruikerService gebruikerService, JwtService jwtService) {
    this.gebruikerService = gebruikerService;
    this.jwtService = jwtService;
  }

  @GetMapping("/{email}")
  public Gebruiker getGebruiker(@PathVariable String email) {
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

  @GetMapping("/email/{token}")
  public String getEmailFromToken(@PathVariable String token) {
    return jwtService.extractEmail(token);
  }

  @GetMapping("/id/{token}")
  public String getIdFromToken(@PathVariable String token) {
    Gebruiker gebruiker = gebruikerService.getGebruikerByEmail(
      jwtService.extractEmail(token)
    );
    return String.valueOf(gebruiker.getId());
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
