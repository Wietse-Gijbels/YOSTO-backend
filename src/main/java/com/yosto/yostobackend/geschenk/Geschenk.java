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

    private String code;

    private boolean isBeschikbaar;

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
        this.gebruiker = builder.gebruiker;
        this.code = builder.code;
        this.isBeschikbaar = builder.isBeschikbaar;
        this.geschenkCategorie = builder.geschenkCategorie;
        this.geschenkCategorie.addGeschenk(this);
    }

    public UUID getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getCode() {
        return code;
    }

    public boolean isBeschikbaar() {
        return isBeschikbaar;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public GeschenkCategorie getGeschenkCategorie() {
        return geschenkCategorie;
    }

    public void updateGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
        this.isBeschikbaar = false;
    }

    public void updateGeschenkCategorie(GeschenkCategorie geschenkCategorie) {
        this.geschenkCategorie = geschenkCategorie;
    }
}
