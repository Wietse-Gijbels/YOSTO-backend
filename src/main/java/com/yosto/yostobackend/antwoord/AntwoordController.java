package com.yosto.yostobackend.antwoord;

import com.yosto.yostobackend.config.JwtService;
import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.gebruiker.GebruikerService;
import com.yosto.yostobackend.generic.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/antwoorden")
public class AntwoordController {

    private final AntwoordService antwoordService;

    private final GebruikerService gebruikerService;

    private final JwtService jwtService;

    public AntwoordController(AntwoordService antwoordService, GebruikerService gebruikerService, JwtService jwtService) {
        this.antwoordService = antwoordService;
        this.gebruikerService = gebruikerService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public List<Antwoord> createAntwoorden(@RequestParam String token, @RequestBody List<AntwoordDTO> antwoordDTOs) {
        return antwoordService.saveAntwoorden(token, antwoordDTOs);
    }

    @GetMapping("/{token}")
    public List<Antwoord> getAntwoordenByGebruiker(@PathVariable String token) {
        Gebruiker gebruiker = gebruikerService.getGebruikerByEmail(jwtService.extractEmail(token));
        return antwoordService.getAntwoordenByGebruiker(gebruiker);
    }

    @GetMapping("/aantal/{token}")
    public int getAmountOfAntwoordenByGebruiker(@PathVariable String token) {
        Gebruiker gebruiker = gebruikerService.getGebruikerByEmail(jwtService.extractEmail(token));
        return antwoordService.getAmountOfAntwoorden(gebruiker);
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
