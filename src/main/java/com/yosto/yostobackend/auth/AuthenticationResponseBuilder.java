package com.yosto.yostobackend.auth;

import com.yosto.yostobackend.gebruiker.Rol;

public final class AuthenticationResponseBuilder {
    String token;
    Rol rol;

    public AuthenticationResponseBuilder() {
    }

    public static AuthenticationResponseBuilder authenticationResponseBuilder() {
        return new AuthenticationResponseBuilder();
    }

    public AuthenticationResponseBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public AuthenticationResponseBuilder setRol(Rol rol) {
        this.rol = rol;
        return this;
    }

    public AuthenticationResponse build() {
        return new AuthenticationResponse(this);
    }
}
