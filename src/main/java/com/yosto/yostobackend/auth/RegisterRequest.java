package com.yosto.yostobackend.auth;

import com.yosto.yostobackend.gebruiker.Rol;
import java.util.List;

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
  private String huidigeStudieAndNiveau;
  private List<String> behaaldeDiplomas;

  public RegisterRequest() {}

  public RegisterRequest(
          String voornaam,
          String achternaam,
          String gebruikersnaam,
          String email,
          String wachtwoord,
          String geslacht,
          int leeftijd,
          String woonplaats,
          Rol rol,
          String huidigeStudieAndNiveau,
          List<String> behaaldeDiplomas
  ) {
    this.voornaam = voornaam;
    this.achternaam = achternaam;
    this.gebruikersnaam = gebruikersnaam;
    this.email = email;
    this.wachtwoord = wachtwoord;
    this.geslacht = geslacht;
    this.leeftijd = leeftijd;
    this.woonplaats = woonplaats;
    this.rol = rol;
    this.huidigeStudieAndNiveau = huidigeStudieAndNiveau;
    this.behaaldeDiplomas = behaaldeDiplomas;
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

  public String getGeslacht() {
    return geslacht;
  }

  public String getGebruikersnaam() {
    return gebruikersnaam;
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

  public String getHuidigeStudieAndNiveau() {
    return huidigeStudieAndNiveau;
  }

  public List<String> getBehaaldeDiplomas() {
    return behaaldeDiplomas;
  }
}
