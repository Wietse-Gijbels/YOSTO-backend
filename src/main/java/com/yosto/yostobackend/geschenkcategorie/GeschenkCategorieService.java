package com.yosto.yostobackend.geschenkcategorie;

import com.yosto.yostobackend.generic.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GeschenkCategorieService {

    @Value("${file.upload-dir}")
    private String fileUploadDir;

    private final GeschenkCategorieRepository geschenkCategorieRepository;

    public GeschenkCategorieService(GeschenkCategorieRepository geschenkCategorieRepository) {
        this.geschenkCategorieRepository = geschenkCategorieRepository;
    }

    public List<GeschenkCategorie> findAll() {
        return geschenkCategorieRepository.findAll();
    }

    public List<GeschenkCategorie> findAllWithBeschikbareGeschenken() {
        return geschenkCategorieRepository.findAllWithAvailableGeschenken();
    }

    public GeschenkCategorie createGeschenkCategorie(GeschenkCategorie geschenkCategorie) {
        Map<String, String> errors = new HashMap<>();

        // Validatiecontrole
        if (geschenkCategorie.getNaam() == null || geschenkCategorie.getNaam().isBlank()
                || geschenkCategorie.getBeschrijving() == null || geschenkCategorie.getBeschrijving().isBlank()
                || geschenkCategorie.getPrijs() < 0 || geschenkCategorie.getFotoUrl() == null || geschenkCategorie.getFotoUrl().isBlank()) {
            errors.put("createGeschenkCategorie", "Dit is geen geldige categorie voor geschenken.");
            throw new ServiceException(errors);
        }

        return geschenkCategorieRepository.save(geschenkCategorie);
    }

    public String storeFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(fileUploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    public String getFileUploadDir() {
        return fileUploadDir;
    }

    public void deleteById(UUID id) {
        if (geschenkCategorieRepository.existsById(id)) {
            geschenkCategorieRepository.deleteById(id);
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("deleteGeschenkCategorie", "GeschenkCategorie met gegeven id bestaat niet.");
            throw new ServiceException(errors);
        }
    }
}
