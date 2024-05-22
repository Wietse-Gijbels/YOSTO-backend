package com.yosto.yostobackend.geschenkcategorie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GeschenkCategorieRepository extends JpaRepository<GeschenkCategorie, UUID> {
}
