package com.yosto.yostobackend.Afstudeerrichting;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yosto.yostobackend.instelling.Instelling;
import com.yosto.yostobackend.studierichting.Studierichting;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "afstudeerrichting")
public class Afstudeerrichting {

    @Id
    @GeneratedValue
    private UUID id;

    private String naam;

    @Column(length = 10000)
    private String beschrijving;

    @ManyToOne
    @JoinColumn(name = "studierichting_id")
    @JsonBackReference
    private Studierichting studierichting;

    @ManyToMany
    @JoinTable(
            name = "afstudeerrichting_instelling",
            joinColumns = @JoinColumn(name = "afstudeerrichting_id"),
            inverseJoinColumns = @JoinColumn(name = "instelling_id"))
    @JsonManagedReference
    private Set<Instelling> instellingen;

    public Afstudeerrichting() {
    }

    public Afstudeerrichting(AfstudeerrichtingBuilder builder) {
        this.naam = builder.naam;
        this.beschrijving = builder.beschrijving;
    }

    public UUID getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public Studierichting getStudierichting() {
        return studierichting;
    }

    public Set<Instelling> getInstellingen() {
        return instellingen;
    }
}
