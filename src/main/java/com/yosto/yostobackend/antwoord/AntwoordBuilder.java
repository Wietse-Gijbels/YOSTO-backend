package com.yosto.yostobackend.antwoord;

import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.vraag.Vraag;

import java.util.UUID;

public final class AntwoordBuilder {
    UUID id;
    Gebruiker gebruiker;
    Vraag vraag;
    String antwoord;

    private AntwoordBuilder() {
    }

    public static AntwoordBuilder antwoordBuilder() {
        return new AntwoordBuilder();
    }

    public AntwoordBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public AntwoordBuilder setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
        return this;
    }

    public AntwoordBuilder setVraag(Vraag vraag) {
        this.vraag = vraag;
        return this;
    }

    public AntwoordBuilder setAntwoord(String antwoord) {
        this.antwoord = antwoord;
        return this;
    }

    public Antwoord build() {
        return new Antwoord(this);
    }
}
