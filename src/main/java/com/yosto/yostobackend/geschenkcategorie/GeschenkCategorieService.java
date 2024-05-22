package com.yosto.yostobackend.geschenkcategorie;

import com.yosto.yostobackend.generic.ServiceException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GeschenkCategorieService {

    private final GeschenkCategorieRepository geschenkCategorieRepository;

    public GeschenkCategorieService(GeschenkCategorieRepository geschenkCategorieRepository) {
        this.geschenkCategorieRepository = geschenkCategorieRepository;
    }

    public List<GeschenkCategorie> findAll() {
        return geschenkCategorieRepository.findAll();
    }

    public GeschenkCategorie createGeschenkCategorie(GeschenkCategorie geschenkCategorie) {
        Map<String, String> errors = new HashMap<>();

        // Validatiecontrole
        if (geschenkCategorie.getNaam() == null || geschenkCategorie.getNaam().isBlank() ||
                geschenkCategorie.getFoto() == null || geschenkCategorie.getFoto().isBlank()) {
            errors.put("createGeschenkCategorie", "Dit is geen geldige categorie voor geschenken.");
            throw new ServiceException(errors);
        }

        GeschenkCategorie nieuwGeschenkCategorie = GeschenkCategorieBuilder.geschenkCategorieBuilder()
                .setNaam(geschenkCategorie.getNaam())
                .setFoto(geschenkCategorie.getFoto())
                .build();
        return geschenkCategorieRepository.save(nieuwGeschenkCategorie);
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
