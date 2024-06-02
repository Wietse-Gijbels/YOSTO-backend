package com.yosto.yostobackend.gebruikerWaardes;

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

    public GebruikerWaardesController(GebruikerWaardesService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GebruikerWaardes> getGebruikerWaardes(@PathVariable UUID id) {
        return new ResponseEntity<>(service.findByGebruikerId(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<GebruikerWaardes> saveGebruikerWaardes(@RequestBody GebruikerWaardes gebruikerWaardes) {
        return new ResponseEntity<>(service.save(gebruikerWaardes), HttpStatus.OK);
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
