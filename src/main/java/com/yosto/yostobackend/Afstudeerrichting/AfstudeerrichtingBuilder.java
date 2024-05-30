package com.yosto.yostobackend.Afstudeerrichting;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yosto.yostobackend.instelling.Instelling;
import com.yosto.yostobackend.studierichting.Studierichting;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.Set;
import java.util.UUID;

public class AfstudeerrichtingBuilder {

    UUID id;

    String naam;

    String beschrijving;

    Set<Studierichting> studierichtingen;

    @ManyToMany
    @JoinTable(
            name = "studierichting_afstudeerrichting",
            joinColumns = @JoinColumn(name = "studierichting_id"),
            inverseJoinColumns = @JoinColumn(name = "afstudeerrichting_id"))
    @JsonManagedReference
    private Set<Instelling> instellingen;


    public AfstudeerrichtingBuilder() {
    }

    public static AfstudeerrichtingBuilder afstudeerrichtingBuilder() {
        return new AfstudeerrichtingBuilder();
    }

    public AfstudeerrichtingBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public AfstudeerrichtingBuilder setNaam(String naam) {
        this.naam = naam;
        return this;
    }

    public AfstudeerrichtingBuilder setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
        return this;
    }

    public AfstudeerrichtingBuilder setStudierichtingen(Set<Studierichting> studierichtingen) {
        this.studierichtingen = studierichtingen;
        return this;
    }


    public Afstudeerrichting build() {
        return new Afstudeerrichting(this);
    }


}

