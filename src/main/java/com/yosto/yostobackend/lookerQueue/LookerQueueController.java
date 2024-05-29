package com.yosto.yostobackend.lookerQueue;

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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/lookerQueue")
public class LookerQueueController {

    private final LookerQueueService lookerQueueService;

    private final GebruikerService gebruikerService;

    public LookerQueueController(LookerQueueService lookerQueueService, GebruikerService gebruikerService) {
        this.lookerQueueService = lookerQueueService;
        this.gebruikerService = gebruikerService;
    }

    @PostMapping("/joinQueue")
    public ResponseEntity<String> joinQueue(@RequestParam UUID lookerId) {
        Gebruiker looker = gebruikerService.getGebruikerById(lookerId);
        lookerQueueService.addLookerToQueue(lookerId);
        return ResponseEntity.ok("Looker added to the queue");
    }

    @GetMapping("/getFirstLooker")
    public ResponseEntity<Object> getFirstLooker(@RequestParam UUID userId) {
        Map<String, String> errors = new HashMap<>();
        Optional<LookerQueue> lookerQueue = lookerQueueService.getFirstLookerInQueue(userId);
        if (lookerQueue.isPresent()) {
            return ResponseEntity.ok(lookerQueue.get().getLookerId());
        } else {
            errors.put("emptyQueue", "The queue is empty.");
            throw new ServiceException(errors);
        }
    }

    @GetMapping("/getAmountOfLookers")
    public ResponseEntity<Map<String, Integer>> getAmountOfLookers() {
        Map<String, Integer> response = new HashMap<>();
        response.put("amount", lookerQueueService.getAmountOfLookersInQueue());
        return ResponseEntity.ok(response);
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