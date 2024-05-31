package com.yosto.yostobackend.gebruikerWaardes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yosto.yostobackend.gebruiker.Gebruiker;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "gebruiker_waardes")
public class GebruikerWaardes {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "gebruiker_id", nullable = false)
    @JsonBackReference
    private Gebruiker gebruiker;

    @Column(nullable = false)
    private int conventioneel;

    @Column(nullable = false)
    private int praktisch;

    @Column(nullable = false)
    private int analytisch;

    @Column(nullable = false)
    private int kunstzinnig;

    @Column(nullable = false)
    private int sociaal;

    @Column(nullable = false)
    private int ondernemend;

    public GebruikerWaardes() {
    }

    public GebruikerWaardes(GebruikerWaardesBuilder builder) {
        this.gebruiker = builder.gebruiker;
        this.conventioneel = builder.conventioneel;
        this.praktisch = builder.praktisch;
        this.analytisch = builder.analytisch;
        this.kunstzinnig = builder.kunstzinnig;
        this.sociaal = builder.sociaal;
        this.ondernemend = builder.ondernemend;
    }

    public UUID getId() {
        return id;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public int getConventioneel() {
        return conventioneel;
    }

    public int getPraktisch() {
        return praktisch;
    }

    public int getAnalytisch() {
        return analytisch;
    }

    public int getKunstzinnig() {
        return kunstzinnig;
    }

    public int getSociaal() {
        return sociaal;
    }

    public int getOndernemend() {
        return ondernemend;
    }
}
