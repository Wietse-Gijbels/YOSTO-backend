package com.yosto.yostobackend.gebruiker;

import java.util.*;

import com.yosto.yostobackend.geschenk.Geschenk;
import com.yosto.yostobackend.studierichting.Studierichting;

import java.util.Set;

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

    Set<Rol> rol;

    Rol actieveRol;

    Studierichting huidigeStudie;

    Set<Studierichting> behaaldeDiplomas;

    List<Geschenk> geschenken;

    List<Studierichting> favoriteStudierichtingen;

    public GebruikerBuilder() {
    }

    public GebruikerBuilder(Gebruiker gebruiker) {
        this.id = gebruiker.getId();
        this.voornaam = gebruiker.getVoornaam();
        this.achternaam = gebruiker.getAchternaam();
        this.gebruikersnaam = gebruiker.getGebruikersnaam();
        this.email = gebruiker.getEmail();
        this.wachtwoord = gebruiker.getWachtwoord();
        this.geslacht = gebruiker.getGeslacht();
        this.leeftijd = gebruiker.getLeeftijd();
        this.woonplaats = gebruiker.getWoonplaats();
        this.rol = new HashSet<>(gebruiker.getRollen());
        this.status = gebruiker.getStatus();
        this.xpAantal = gebruiker.getXpAantal();
        this.actieveRol = gebruiker.getActieveRol();
        this.huidigeStudie = gebruiker.getHuidigeStudie();
        this.behaaldeDiplomas = new HashSet<>(gebruiker.getBehaaldeDiplomas());
        this.geschenken = new ArrayList<>(gebruiker.getGeschenken());
        this.favoriteStudierichtingen = new ArrayList<>(gebruiker.getFavorieteStudierichtingen());
    }

    public GebruikerBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public GebruikerBuilder setGeschenken(List<Geschenk> geschenken) {
        this.geschenken = geschenken;
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

    public GebruikerBuilder setRol(Set<Rol> rol) {
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

    public GebruikerBuilder setActieveRol(Rol actieveRol) {
        this.actieveRol = actieveRol;
        return this;
    }

    public GebruikerBuilder setHuidigeStudie(Studierichting huidigeStudie) {
        this.huidigeStudie = huidigeStudie;
        return this;
    }

    public GebruikerBuilder setBehaaldeDiplomas(Set<Studierichting> behaaldeDiplomas) {
        this.behaaldeDiplomas = behaaldeDiplomas;
        return this;
    }

    public Gebruiker build() {
        return new Gebruiker(this);
    }
}
