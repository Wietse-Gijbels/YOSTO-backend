package com.yosto.yostobackend.studierichting;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "studierichting")
public class Studierichting {

    @Id
    @GeneratedValue
    private UUID id;

    private String naam;

    private String soort;


    public Studierichting() {
    }

    public Studierichting(StudierichtingBuilder builder) {
        this.naam = builder.naam;
        this.soort = builder.soort;
    }

    public UUID getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getSoort() {
        return soort;
    }
}
