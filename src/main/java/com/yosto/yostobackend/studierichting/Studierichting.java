package com.yosto.yostobackend.studierichting;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yosto.yostobackend.Afstudeerrichting.Afstudeerrichting;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "studierichting")
public class Studierichting {

    @Id
    @GeneratedValue
    private UUID id;

    private String naam;

    private String studiepunten;

    private String niveauNaam;

    @OneToMany(mappedBy = "studierichting", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Afstudeerrichting> afstudeerrichtingen;

    public Studierichting() {
    }

    public Studierichting(StudierichtingBuilder builder) {
        this.naam = builder.naam;
        this.studiepunten = builder.studiepunten;
        this.niveauNaam = builder.niveauNaam;
        this.afstudeerrichtingen = builder.afstudeerrichtingen;
    }

    public UUID getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getStudiepunten() {
        return studiepunten;
    }

    public String getNiveauNaam() {
        return niveauNaam;
    }

    public Set<Afstudeerrichting> getAfstudeerrichtingen() {
        return afstudeerrichtingen;
    }
}
