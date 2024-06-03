package com.yosto.yostobackend.gebruiker;

import java.util.UUID;

public final class GebruikerBuilder {
    UUID id;

    String voornaam;

    String achternaam;

    String gebruikersnaam;

    String email;

    String wachtwoord;

    String geslacht;

    int leeftijd;

    String woonplaats;

    Status status;

    int xpAantal;

    Rol rol;

    public GebruikerBuilder() {
    }

    public static GebruikerBuilder gebruikerBuilder() {
        return new GebruikerBuilder();
    }

    public GebruikerBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public GebruikerBuilder setVoornaam(String voornaam) {
        this.voornaam = voornaam;
        return this;
    }

    public GebruikerBuilder setAchternaam(String achternaam) {
        this.achternaam = achternaam;
        return this;
    }

    public GebruikerBuilder setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
        return this;
    }

    public GebruikerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public GebruikerBuilder setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
        return this;
    }

    public GebruikerBuilder setGeslacht(String geslacht) {
        this.geslacht = geslacht;
        return this;
    }

    public GebruikerBuilder setLeeftijd(int leeftijd) {
        this.leeftijd = leeftijd;
        return this;
    }

    public GebruikerBuilder setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
        return this;
    }

    public GebruikerBuilder setRol(Rol rol) {
        this.rol = rol;
        return this;
    }

    public GebruikerBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public GebruikerBuilder setXpAantal(int xpAantal) {
        this.xpAantal = xpAantal;
        return this;
    }

    public Gebruiker build() {
        return new Gebruiker(this);
    }
}
