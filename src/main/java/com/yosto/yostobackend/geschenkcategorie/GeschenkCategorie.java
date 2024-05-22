package com.yosto.yostobackend.geschenkcategorie;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.yosto.yostobackend.geschenk.Geschenk;

@Entity
@Table(name = "geschenk_categorie")
public class GeschenkCategorie {

    @Id
    @GeneratedValue
    private UUID id;

    private String naam;

    private String foto;

    @OneToMany(mappedBy = "geschenkCategorie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Geschenk> geschenken = new ArrayList<>();

    public GeschenkCategorie() {
    }

    public GeschenkCategorie(GeschenkCategorieBuilder builder) {
        this.naam = builder.naam;
        this.foto = builder.foto;
    }

    public UUID getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getFoto() {
        return foto;
    }

    public List<Geschenk> getGeschenken() {
        return geschenken;
    }
}
