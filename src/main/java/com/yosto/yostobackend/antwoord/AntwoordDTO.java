package com.yosto.yostobackend.antwoord;

import java.util.UUID;

public class AntwoordDTO {

    private UUID vraagId;
    private String antwoord;

    // Getters and setters
    public UUID getVraagId() {
        return vraagId;
    }

    public void setVraagId(UUID vraagId) {
        this.vraagId = vraagId;
    }

    public String getAntwoord() {
        return antwoord;
    }

    public void setAntwoord(String antwoord) {
        this.antwoord = antwoord;
    }
}
