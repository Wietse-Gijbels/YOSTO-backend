package com.yosto.yostobackend.auth;

import com.yosto.yostobackend.generic.ServiceException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
  private final AuthenticationService service;

  public AuthenticationController(AuthenticationService service) {
    this.service = service;
  }

  @PostMapping("/registreer")
  public AuthenticationResponse registreer(@RequestBody RegisterRequest request) throws IOException {
    return service.registreer(request);
  }

  @PostMapping("/login")
  public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
    return service.login(request);
  }

  @PostMapping("/verify")
  public ResponseEntity<Void> verifyAccount(@RequestParam String email, @RequestParam String code) {
    service.verifyAccount(email, code);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/switch")
    public ResponseEntity<Void> switchRol(@RequestBody String token) {
        service.switchRol(token);
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
