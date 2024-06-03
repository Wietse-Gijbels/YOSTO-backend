package com.yosto.yostobackend.studierichting;

import com.yosto.yostobackend.Afstudeerrichting.Afstudeerrichting;

import java.util.List;
import java.util.UUID;

public class StudierichtingBuilder {
    UUID id;

    String naam;

    String studiepunten;

    String niveauNaam;

    String afstudeerrichting;

    List<Afstudeerrichting> afstudeerrichtingen;

    String beschrijving;


    public StudierichtingBuilder() {
    }

    public static StudierichtingBuilder studieRichtingBuilder() {
        return new StudierichtingBuilder();
    }

    public StudierichtingBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public StudierichtingBuilder setNaam(String naam) {
        this.naam = naam;
        return this;
    }

    public StudierichtingBuilder setStudiepunten(String studiepunten) {
        this.studiepunten = studiepunten;
        return this;
    }

    public StudierichtingBuilder setNiveauNaam(String niveauNaam) {
        this.niveauNaam = niveauNaam;
        return this;

    }

    public StudierichtingBuilder setAfstudeerrichting(String afstudeerrichting) {
        this.afstudeerrichting = afstudeerrichting;
        return this;
    }

    public StudierichtingBuilder setAfstudeerrichtingen(List<Afstudeerrichting> afstudeerrichtingen) {
        this.afstudeerrichting = afstudeerrichting;
        return this;
    }

    public StudierichtingBuilder setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
        return this;
    }


    public Studierichting build() {
        return new Studierichting(this);
    }


}
