package com.yosto.yostobackend.studierichting;

import com.yosto.yostobackend.config.JwtService;
import com.yosto.yostobackend.generic.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/studierichting")
public class StudierichtingController {

    private final StudierichtingService studierichtingService;

    private final JwtService jwtService;

    public StudierichtingController(StudierichtingService studierichtingService, JwtService jwtService) {
        this.studierichtingService = studierichtingService;
        this.jwtService = jwtService;
    }

    @GetMapping("/dto")
    public List<String> findAllStudierichtingDTOs() {
        return studierichtingService.findAllStudierichtingDTOs();
    }

    @GetMapping("/hoger-onderwijs/dto/{token}")
    public List<String> findAllStudierichtingDTOsToevoeging(@PathVariable String token) {
        return studierichtingService.findAllStudierichtingDTOsToevoeging(jwtService.extractEmail(token));
    }

    @GetMapping("/all/{page}")
    public Page<Studierichting> findAll(@PathVariable() int page) {
        return studierichtingService.findAll(page, 10);
    }

    @GetMapping("/hoger-onderwijs/dto")
    public List<String> findHogerOnderwijsRichtingenDTOs(@RequestParam(required = false) String filter) {
        return studierichtingService.findHogerOnderwijsRichtingenDTOs(filter);
    }

    @GetMapping("/middelbaar-onderwijs/dto")
    public List<String> findMiddelbaarOnderwijsRichtingenDTOs(@RequestParam(required = false) String filter) {
        return studierichtingService.findMiddelbaarOnderwijsRichtingenDTOs(filter);
    }

    @GetMapping("/all/dto")
    public List<String> findAllRichtingenDTOs(@RequestParam(required = false) String filter) {
        return studierichtingService.findAllRichtingenDTOs(filter);
    }

    @GetMapping("/{id}")
    public Studierichting findStudierichtingById(@PathVariable() UUID id) {
        return studierichtingService.findStudierichtingById(id);
    }

    @GetMapping("/filter")
    public Page<Studierichting> getFilteredStudierichtingen(
            @RequestParam(required = false) String naam,
            @RequestParam(required = false) String niveauNaam,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return studierichtingService.findHogerOnderwijsRichtingenByNaamAndNiveauNaam(naam, niveauNaam, sortOrder, page, size);
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
