package com.yosto.yostobackend.antwoord;

import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.gebruiker.GebruikerBuilder;
import com.yosto.yostobackend.vraag.Vraag;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "antwoorden")
public class Antwoord {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "gebruiker_id", nullable = false)
    private Gebruiker gebruiker;

    @ManyToOne
    @JoinColumn(name = "vraag_id", nullable = false)
    private Vraag vraag;

    @Column(nullable = false)
    private String antwoord;

    public Antwoord(AntwoordBuilder builder) {
        this.id = builder.id;
        this.gebruiker = builder.gebruiker;
        this.vraag = builder.vraag;
        this.antwoord = builder.antwoord;
    }

    public Antwoord() {
    }

    public UUID getId() {
        return id;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public Vraag getVraag() {
        return vraag;
    }

    public String getAntwoord() {
        return antwoord;
    }
}
