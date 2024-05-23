package com.yosto.yostobackend.geschenkcategorie;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.yosto.yostobackend.geschenk.Geschenk;

@Entity
@Table(name = "geschenk_categorie")
public class GeschenkCategorie {

    @Id
    @GeneratedValue
    private UUID id;

    private String naam;

    private int prijs;

    private String beschrijving;

    private String fotoUrl;  // Nieuw veld toegevoegd

    @OneToMany(mappedBy = "geschenkCategorie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "categorie-geschenken")
    private List<Geschenk> geschenken = new ArrayList<>();

    public GeschenkCategorie() {
    }

    public GeschenkCategorie(GeschenkCategorieBuilder builder) {
        this.naam = builder.naam;
        this.prijs = builder.prijs;
        this.beschrijving = builder.beschrijving;
        this.fotoUrl = builder.fotoUrl;  // Nieuw veld toegevoegd
    }

    public UUID getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getPrijs() {
        return prijs;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public List<Geschenk> getGeschenken() {
        return geschenken;
    }

    public void addGeschenk(Geschenk geschenk) {
        geschenken.add(geschenk);
        geschenk.updateGeschenkCategorie(this);
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
