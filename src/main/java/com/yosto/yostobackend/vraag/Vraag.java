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

    public Vraag() {
    }

    public Vraag(String vraagTekst, String parameter) {
        this.vraagTekst = vraagTekst;
        this.parameter = parameter;
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
}
