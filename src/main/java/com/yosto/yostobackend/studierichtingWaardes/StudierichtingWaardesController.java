package com.yosto.yostobackend.studierichtingWaardes;

import com.yosto.yostobackend.generic.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/studierichtingWaardes")
public class StudierichtingWaardesController {

    private final StudierichtingWaardesService service;

    public StudierichtingWaardesController(StudierichtingWaardesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StudierichtingWaardes>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{studierichtingId}")
    public ResponseEntity<StudierichtingWaardes> getByStudierichtingId(@PathVariable UUID studierichtingId) {
        return new ResponseEntity<>(service.findByStudierichtingId(studierichtingId), HttpStatus.OK);
    }

    @PostMapping("/similarity")
    public ResponseEntity<List<StudierichtingSimilarityDto>> calculateSimilarity(@RequestBody UserValuesDto userValues) {
        return new ResponseEntity<>(service.calculateSimilarity(userValues), HttpStatus.OK);
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
