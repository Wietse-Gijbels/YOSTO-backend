package com.yosto.yostobackend.studierichtingWaardes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yosto.yostobackend.studierichting.Studierichting;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "studierichting_waardes")
public class StudierichtingWaardes {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "studierichting_id", nullable = false)
    @JsonBackReference
    private Studierichting studierichting;

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

    public StudierichtingWaardes() {
    }

    public StudierichtingWaardes(StudierichtingWaardesBuilder builder) {
        this.studierichting = builder.studierichting;
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

    public Studierichting getStudierichting() {
        return studierichting;
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