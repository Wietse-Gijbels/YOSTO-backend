package com.yosto.yostobackend.auth;

import com.yosto.yostobackend.gebruiker.Rol;

public class RegisterRequest {

    private String voornaam;

    private String achternaam;

    private String gebruikersnaam;

    private String email;

    private String wachtwoord;

    private String geslacht;

    private int leeftijd;

    private String woonplaats;

    private Rol rol;

    public RegisterRequest() {
    }

    public RegisterRequest(String voornaam, String achternaam, String gebruikersnaam, String email, String wachtwoord, String geslacht, int leeftijd, String woonplaats, Rol rol) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.gebruikersnaam = gebruikersnaam;
        this.email = email;
        this.wachtwoord = wachtwoord;
        this.geslacht = geslacht;
        this.leeftijd = leeftijd;
        this.woonplaats = woonplaats;
        this.rol = rol;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getEmail() {
        return email;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public int getLeeftijd() {
        return leeftijd;
    }

    public String getWoonplaats() {
        return woonplaats;
    }


    public Rol getRol() {
        return rol;
    }
}
