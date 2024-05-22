package com.yosto.yostobackend.geschenk;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.UUID;
import com.yosto.yostobackend.gebruiker.Gebruiker;
import com.yosto.yostobackend.geschenkcategorie.GeschenkCategorie;

@Entity
@Table(name = "geschenk")
public class Geschenk {

    @Id
    @GeneratedValue
    private UUID id;

    private String titel;

    private String beschrijving;

    private double prijs;

    @ManyToOne
    @JoinColumn(name = "gebruiker_id")
    @JsonBackReference(value = "gebruiker-geschenken")
    private Gebruiker gebruiker;

    @ManyToOne
    @JoinColumn(name = "geschenk_categorie_id", nullable = false)
    @JsonBackReference(value = "categorie-geschenken")
    private GeschenkCategorie geschenkCategorie;

    public Geschenk() {
    }

    public Geschenk(GeschenkBuilder builder) {
        this.titel = builder.titel;
        this.beschrijving = builder.beschrijving;
        this.prijs = builder.prijs;
        this.gebruiker = builder.gebruiker;
        this.geschenkCategorie = builder.geschenkCategorie;
        this.geschenkCategorie.addGeschenk(this);
    }

    public UUID getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public GeschenkCategorie getGeschenkCategorie() {
        return geschenkCategorie;
    }

    public void updateGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public void updateGeschenkCategorie(GeschenkCategorie geschenkCategorie) {
        this.geschenkCategorie = geschenkCategorie;
    }
}
