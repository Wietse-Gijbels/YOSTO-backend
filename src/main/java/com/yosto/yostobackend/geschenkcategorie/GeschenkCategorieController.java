package com.yosto.yostobackend.geschenkcategorie;

import com.yosto.yostobackend.generic.ServiceException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ResponseEntity<GeschenkCategorie> createGeschenkCategorie(
            @RequestParam("naam") String naam,
            @RequestParam("prijs") int prijs,
            @RequestParam("beschrijving") String beschrijving,
            @RequestParam("file") MultipartFile file) throws IOException {


            String fileName = geschenkCategorieService.storeFile(file);
            GeschenkCategorie geschenkCategorie = new GeschenkCategorieBuilder()
                    .setNaam(naam)
                    .setPrijs(prijs)
                    .setBeschrijving(beschrijving)
                    .setFotoUrl(fileName)
                    .build();
            GeschenkCategorie gc = geschenkCategorieService.createGeschenkCategorie(geschenkCategorie);
            return ResponseEntity.status(HttpStatus.CREATED).body(gc);

    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = geschenkCategorieService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(geschenkCategorieService.getFileUploadDir()).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all")
    public List<GeschenkCategorie> findAll() {
        return geschenkCategorieService.findAll();
    }

    @GetMapping("/{id}")
    public GeschenkCategorie findById(@PathVariable UUID id) {
        return geschenkCategorieService.findById(id);
    }

    @GetMapping("/all-beschikbaar")
    public List<GeschenkCategorie> findAllBeschikbaar() {
        return geschenkCategorieService.findAllWithBeschikbareGeschenken();
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
