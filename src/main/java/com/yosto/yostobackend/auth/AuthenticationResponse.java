package com.yosto.yostobackend.auth;

import com.yosto.yostobackend.gebruiker.Rol;

public class AuthenticationResponse {
    private String token;
    private Rol rol;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(
            AuthenticationResponseBuilder authenticationResponseBuilder
    ) {
        this.token = authenticationResponseBuilder.token;
        this.rol = authenticationResponseBuilder.rol;
    }

    public String getToken() {
        return token;
    }

    public Rol getRol() {
        return rol;
    }
}
