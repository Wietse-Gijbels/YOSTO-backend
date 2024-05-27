package com.yosto.yostobackend.instelling;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yosto.yostobackend.studierichting.Studierichting;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "instelling")
public class Instelling {

    @Id
    @GeneratedValue
    private UUID id;

    private String naam;

    private String campus;

    private String postcode;

    private String gemeente;

    private String adres;

    @ManyToMany(mappedBy = "instellingen")
    @JsonBackReference
    Set<Studierichting> studierichtingen;


    public Instelling() {
    }

    public Instelling(InstellingBuilder builder) {
        this.naam = builder.naam;
        this.campus = builder.campus;
        this.postcode = builder.postcode;
        this.gemeente = builder.gemeente;
        this.adres = builder.adres;
        this.studierichtingen = builder.studierichtingen;
    }

    public UUID getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getCampus() {
        return campus;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getGemeente() {
        return gemeente;
    }

    public String getAdres() {
        return adres;
    }

    public Set<Studierichting> getStudierichtingen() {
        return studierichtingen;
    }
}
