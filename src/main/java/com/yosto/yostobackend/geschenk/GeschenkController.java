package com.yosto.yostobackend.geschenk;

import com.yosto.yostobackend.generic.ServiceException;
import com.yosto.yostobackend.gebruiker.GebruikerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/geschenk")
public class GeschenkController {

    private final GeschenkService geschenkService;
    private final GebruikerService gebruikerService;

    public GeschenkController(GeschenkService geschenkService, GebruikerService gebruikerService) {
        this.geschenkService = geschenkService;
        this.gebruikerService = gebruikerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Geschenk> createGeschenk(@RequestBody Geschenk geschenk) {
        Geschenk g = geschenkService.createGeschenk(geschenk);
        return ResponseEntity.status(HttpStatus.CREATED).body(g);
    }

    @GetMapping("/all")
    public List<Geschenk> findAll() {
        return geschenkService.findAll();
    }

    @PostMapping("/addToGebruiker/{gebruikerId}/{geschenkCategorieId}")
    public ResponseEntity<Void> addGeschenkToGebruiker(@PathVariable UUID gebruikerId, @PathVariable UUID geschenkCategorieId) throws IOException {
        gebruikerService.addGeschenkToGebruiker(gebruikerId, geschenkCategorieId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ServiceException.class})
    public Map<String, String> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors()
                    .forEach(error -> {
                        String fieldName = ((FieldError) error).getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.put("error", fieldName + ": " + errorMessage);
                    });
        } else if (ex instanceof ServiceException) {
            errors.putAll(((ServiceException) ex).getErrors());
        } else {
            errors.put("error", ex.getMessage());
        }
        return errors;
    }
}
