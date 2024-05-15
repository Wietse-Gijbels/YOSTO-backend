package com.yosto.yostobackend.auth;

public final class AuthenticationResponseBuilder {

    String token;

    public AuthenticationResponseBuilder() {
    }

    public static AuthenticationResponseBuilder authenticationResponseBuilder() {
        return new AuthenticationResponseBuilder();
    }

    public AuthenticationResponseBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public AuthenticationResponse build() {
        return new AuthenticationResponse(this);
    }
}
