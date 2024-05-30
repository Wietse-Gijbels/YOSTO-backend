package com.yosto.yostobackend.instelling;

import com.yosto.yostobackend.Afstudeerrichting.Afstudeerrichting;

import java.util.Set;
import java.util.UUID;

public class InstellingBuilder {

    UUID id;

    String naam;

    String campus;

    String postcode;

    String gemeente;

    String adres;

    Set<Afstudeerrichting> afstudeerrichtingen;


    public InstellingBuilder() {
    }

    public static InstellingBuilder instellingBuilder() {
        return new InstellingBuilder();
    }

    public InstellingBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public InstellingBuilder setNaam(String naam) {
        this.naam = naam;
        return this;
    }

    public InstellingBuilder setCampus(String campus) {
        this.campus = campus;
        return this;
    }

    public InstellingBuilder setPostcode(String postcode) {
        this.postcode = postcode;
        return this;

    }

    public InstellingBuilder setGemeente(String gemeente) {
        this.gemeente = gemeente;
        return this;
    }

    public InstellingBuilder setAdres(String adres) {
        this.adres = adres;
        return this;
    }

    public InstellingBuilder setAfstudeerringting(Set<Afstudeerrichting> afstudeerringtingen) {
        this.afstudeerrichtingen = afstudeerringtingen;
        return this;
    }


    public Instelling build() {
        return new Instelling(this);
    }


}
