package com.yosto.yostobackend.auth;

public class AuthenticationRequest {
  private String email;

  private String wachtwoord;

  public AuthenticationRequest() {}

  public AuthenticationRequest(String email, String wachtwoord) {
    this.email = email;
    this.wachtwoord = wachtwoord;
  }

  public String getEmail() {
    return email;
  }

  public String getWachtwoord() {
    return wachtwoord;
  }
}
