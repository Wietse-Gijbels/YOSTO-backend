package com.yosto.yostobackend.geschenkcategorie;

import java.util.UUID;

public class GeschenkCategorieBuilder {
    UUID id;

    String naam;

    int prijs;

    String beschrijving;

    public GeschenkCategorieBuilder() {
    }

    public static GeschenkCategorieBuilder geschenkCategorieBuilder() {
        return new GeschenkCategorieBuilder();
    }

    public GeschenkCategorieBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public GeschenkCategorieBuilder setNaam(String naam) {
        this.naam = naam;
        return this;
    }

    public GeschenkCategorieBuilder setPrijs(int prijs) {
        this.prijs = prijs;
        return this;
    }

    public GeschenkCategorieBuilder setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
        return this;
    }

    public GeschenkCategorie build() {
        return new GeschenkCategorie(this);
    }
}
