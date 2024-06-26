package com.yosto.yostobackend.geschenk;

import java.util.UUID;
import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.geschenkcategorie.GeschenkCategorie;

public class GeschenkBuilder {
    UUID id;

    String titel;

    String code;

    boolean isBeschikbaar;

    Gebruiker gebruiker;

    GeschenkCategorie geschenkCategorie;

    public GeschenkBuilder() {
    }

    public static GeschenkBuilder geschenkBuilder() {
        return new GeschenkBuilder();
    }

    public GeschenkBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public GeschenkBuilder setTitel(String titel) {
        this.titel = titel;
        return this;
    }

    public GeschenkBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public GeschenkBuilder setBeschikbaar(boolean isBeschikbaar) {
        this.isBeschikbaar = isBeschikbaar;
        return this;
    }

    public GeschenkBuilder setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
        return this;
    }

    public GeschenkBuilder setGeschenkCategorie(GeschenkCategorie geschenkCategorie) {
        this.geschenkCategorie = geschenkCategorie;
        return this;
    }

    public Geschenk build() {
        return new Geschenk(this);
    }
}
