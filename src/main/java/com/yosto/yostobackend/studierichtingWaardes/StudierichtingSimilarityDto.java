package com.yosto.yostobackend.studierichtingWaardes;

import java.util.UUID;

public class StudierichtingSimilarityDto {
    private UUID studierichtingId;
    private String naam;
    private String niveauNaam;
    private int conventioneel;
    private int praktisch;
    private int analytisch;
    private int kunstzinnig;
    private int sociaal;
    private int ondernemend;
    private double similarityPercentage;

    public StudierichtingSimilarityDto(UUID studierichtingId, String naam, String niveauNaam, int conventioneel, int praktisch, int analytisch, int kunstzinnig, int sociaal, int ondernemend, double similarityPercentage) {
        this.studierichtingId = studierichtingId;
        this.naam = naam;
        this.niveauNaam = niveauNaam;
        this.conventioneel = conventioneel;
        this.praktisch = praktisch;
        this.analytisch = analytisch;
        this.kunstzinnig = kunstzinnig;
        this.sociaal = sociaal;
        this.ondernemend = ondernemend;
        this.similarityPercentage = similarityPercentage;
    }

    public UUID getStudierichtingId() {
        return studierichtingId;
    }

    public String getNaam() {
        return naam;
    }

    public String getNiveauNaam() {
        return niveauNaam;
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

    public double getSimilarityPercentage() {
        return similarityPercentage;
    }
}
