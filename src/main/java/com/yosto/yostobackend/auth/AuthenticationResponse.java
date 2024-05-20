package com.yosto.yostobackend.auth;

public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(AuthenticationResponseBuilder authenticationResponseBuilder) {
        this.token = authenticationResponseBuilder.token;
    }

    public String getToken() {
        return token;
    }
}
