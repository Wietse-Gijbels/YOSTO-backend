package com.yosto.yostobackend.vraag;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "vragen")
public class Vraag {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String vraagTekst;

    @Column(nullable = false)
    private String parameter;

    @Column(nullable = false)
    private String fotourl;

    public Vraag() {
    }

    public Vraag(String vraagTekst, String parameter, String fotoUrl) {
        this.vraagTekst = vraagTekst;
        this.parameter = parameter;
        this.fotourl = fotoUrl;
    }

    public UUID getId() {
        return id;
    }

    public String getVraagTekst() {
        return vraagTekst;
    }

    public String getParameter() {
        return parameter;
    }

    public String getFotoUrl() {
        return fotourl;
    }
}
