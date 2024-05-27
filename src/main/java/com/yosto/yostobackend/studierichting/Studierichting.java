package com.yosto.yostobackend.studierichting;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yosto.yostobackend.instelling.Instelling;
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

    private String afstudeerrichting;

    @Column(length = 10000)
    private String beschrijving;


    @ManyToMany
    @JoinTable(
            name = "studierichting_instelling",
            joinColumns = @JoinColumn(name = "studierichting_id"),
            inverseJoinColumns = @JoinColumn(name = "instelling_id"))
    @JsonManagedReference
    private Set<Instelling> instellingen;

    public Studierichting() {
    }

    public Studierichting(StudierichtingBuilder builder) {
        this.naam = builder.naam;
        this.studiepunten = builder.studiepunten;
        this.niveauNaam = builder.niveauNaam;
        this.afstudeerrichting = builder.afstudeerrichting;
        this.instellingen = builder.instellingen;
        this.beschrijving = builder.beschrijving;
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

    public String getAfstudeerrichting() {
        return afstudeerrichting;
    }

    public Set<Instelling> getInstellingen() {
        return instellingen;
    }

    public String getBeschrijving() {
        return beschrijving;
    }
}
