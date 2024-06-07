package com.yosto.yostobackend.gebruiker;

import com.yosto.yostobackend.config.JwtService;
import com.yosto.yostobackend.generic.ServiceException;
import com.yosto.yostobackend.lookerQueue.LookerQueueService;
import com.yosto.yostobackend.studierichting.Studierichting;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

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

    @PostMapping("/addFavorieteStudierichting/{studierichtingId}")
    public ResponseEntity<Void> addFavorieteStudierichtingToGebruiker(@RequestHeader("Authorization") String authorizationHeader, @PathVariable UUID studierichtingId) throws IOException {
        String token = extractToken(authorizationHeader);
        Gebruiker gebruiker = gebruikerService.getGebruikerByEmail(jwtService.extractEmail(token));
        gebruikerService.addFavorieteStudierichting(gebruiker, studierichtingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/removeFavorieteStudierichting/{studierichtingId}")
    public ResponseEntity<Void> removeFavorieteStudierichtingToGebruiker(@RequestHeader("Authorization") String authorizationHeader, @PathVariable UUID studierichtingId) throws IOException {
        String token = extractToken(authorizationHeader);
        Gebruiker gebruiker = gebruikerService.getGebruikerByEmail(jwtService.extractEmail(token));
        gebruikerService.removeFavorieteStudierichting(gebruiker, studierichtingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("favorietenStudierichtingen/{page}")
    public Page<Studierichting> findAllFavorieteStudierichtingen(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int page, @RequestParam(defaultValue = "10") int size) throws IOException {
        String token = extractToken(authorizationHeader);
        Gebruiker gebruiker = gebruikerService.getGebruikerByEmail(jwtService.extractEmail(token));
        return gebruikerService.findAllFavorieteStudierichtingen(gebruiker, page, 10);
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


    @GetMapping("/rol/{token}")
    public Rol getRoleFromToken(@PathVariable String token) {
        return gebruikerService.getRoleByEmail(jwtService.extractEmail(token));
    }

    @GetMapping("/rollen")
    public Set<Rol> getLookerQueue(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        return gebruikerService.getRollen(jwtService.extractEmail(token));
    }

    @PutMapping("/rol")
    public Gebruiker updateRole(@RequestBody Rol updateRoleDTO, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        return gebruikerService.updateRole(updateRoleDTO, jwtService.extractEmail(token));
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Remove "Bearer " prefix
        }
        throw new IllegalArgumentException("Invalid Authorization header.");
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
