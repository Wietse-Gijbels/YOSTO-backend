package com.yosto.yostobackend.geschenk;

import com.yosto.yostobackend.generic.ServiceException;
import com.yosto.yostobackend.geschenkcategorie.GeschenkCategorie;
import com.yosto.yostobackend.geschenkcategorie.GeschenkCategorieRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GeschenkService {

    private final GeschenkRepository geschenkRepository;

    private final GeschenkCategorieRepository geschenkCategorieRepository;

    public GeschenkService(GeschenkRepository geschenkRepository, GeschenkCategorieRepository geschenkCategorieRepository) {
        this.geschenkRepository = geschenkRepository;
        this.geschenkCategorieRepository = geschenkCategorieRepository;
    }

    public List<Geschenk> findAll() {
        return geschenkRepository.findAll();
    }

    public Optional<Geschenk> findById(UUID id) {
        return geschenkRepository.findById(id);
    }

    public Geschenk createGeschenk(Geschenk geschenk) {
        Map<String, String> errors = new HashMap<>();

        // Validatiecontrole
        if (geschenk.getTitel() == null || geschenk.getTitel().isBlank() ||
                geschenk.getBeschrijving() == null || geschenk.getBeschrijving().isBlank() ||
                geschenk.getPrijs() < 0 ||
                geschenk.getGeschenkCategorie() == null || geschenk.getGeschenkCategorie().getId() == null) {
            errors.put("createGeschenk", "Dit is geen geldig geschenk.");
            throw new ServiceException(errors);
        }

        // Controleer of de geschenkCategorie bestaat
        Optional<GeschenkCategorie> categorieOpt = geschenkCategorieRepository.findById(geschenk.getGeschenkCategorie().getId());
        if (categorieOpt.isEmpty()) {
            errors.put("createGeschenk", "Dit is geen geldige categorie.");
            throw new ServiceException(errors);
        }

        GeschenkCategorie categorie = categorieOpt.get();
        Geschenk nieuwGeschenk = GeschenkBuilder.geschenkBuilder()
                .setTitel(geschenk.getTitel())
                .setBeschrijving(geschenk.getBeschrijving())
                .setPrijs(geschenk.getPrijs())
                .setGeschenkCategorie(categorie)
                .build();
        return geschenkRepository.save(nieuwGeschenk);
    }
}
