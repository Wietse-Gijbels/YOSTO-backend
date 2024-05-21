package com.yosto.yostobackend.studierichting;

import java.util.UUID;

public class StudierichtingBuilder {
    UUID id;

    String naam;

    String soort;


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

    public StudierichtingBuilder setSoort(String soort) {
        this.soort = soort;
        return this;
    }

    public Studierichting build() {
        return new Studierichting(this);
    }


}
