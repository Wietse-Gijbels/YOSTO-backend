package com.yosto.yostobackend.geschenkcategorie;

import java.util.UUID;

public class GeschenkCategorieBuilder {
    UUID id;

    String naam;

    String foto;

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

    public GeschenkCategorieBuilder setFoto(String foto) {
        this.foto = foto;
        return this;
    }

    public GeschenkCategorie build() {
        return new GeschenkCategorie(this);
    }
}
