package com.yosto.yostobackend.gebruikerWaardes;

import com.yosto.yostobackend.config.JwtService;
import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.gebruiker.GebruikerService;
import com.yosto.yostobackend.generic.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/gebruikerWaardes")
public class GebruikerWaardesController {

    private final GebruikerWaardesService service;

    private final GebruikerService gebruikerService;

    private final JwtService jwtService;

    public GebruikerWaardesController(GebruikerWaardesService service, GebruikerService gebruikerService, JwtService jwtService) {
        this.service = service;
        this.gebruikerService = gebruikerService;
        this.jwtService = jwtService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GebruikerWaardes> getGebruikerWaardes(@PathVariable UUID id) {
        return new ResponseEntity<>(service.findByGebruikerId(id), HttpStatus.OK);
    }

    @PostMapping("/{token}/calculate")
    public GebruikerWaardes calculateGebruikerWaardes(@PathVariable String token) {
        Gebruiker gebruiker = gebruikerService.getGebruikerByEmail(jwtService.extractEmail(token));
        return service.calculateGebruikerWaardes(gebruiker);
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
