package com.yosto.yostobackend.studierichting;

public class StudierichtingDTO {
    private String naam;
    private String niveauNaam;

    public StudierichtingDTO(String naam, String niveauNaam) {
        this.naam = naam;
        this.niveauNaam = niveauNaam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNiveauNaam() {
        return niveauNaam;
    }

    public void setNiveauNaam(String niveauNaam) {
        this.niveauNaam = niveauNaam;
    }

    @Override
    public String toString() {
        return naam + " (" + niveauNaam + ")";
    }
}
