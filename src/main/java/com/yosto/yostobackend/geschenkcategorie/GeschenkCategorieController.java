package com.yosto.yostobackend.geschenkcategorie;

import com.yosto.yostobackend.generic.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/geschenkcategorie")
public class GeschenkCategorieController {

    private final GeschenkCategorieService geschenkCategorieService;

    public GeschenkCategorieController(GeschenkCategorieService geschenkCategorieService) {
        this.geschenkCategorieService = geschenkCategorieService;
    }

    @PostMapping("/create")
    public ResponseEntity<GeschenkCategorie> createGeschenkCategorie(@RequestBody GeschenkCategorie geschenkCategorie) {
        GeschenkCategorie gc = geschenkCategorieService.createGeschenkCategorie(geschenkCategorie);
        return ResponseEntity.status(HttpStatus.CREATED).body(gc);
    }

    @GetMapping("/all")
    public List<GeschenkCategorie> findAll() {
        return geschenkCategorieService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        geschenkCategorieService.deleteById(id);
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
