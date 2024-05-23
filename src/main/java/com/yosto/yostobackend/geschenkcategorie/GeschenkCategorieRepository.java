package com.yosto.yostobackend.geschenkcategorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GeschenkCategorieRepository extends JpaRepository<GeschenkCategorie, UUID> {

    @Query("SELECT DISTINCT gc FROM GeschenkCategorie gc JOIN gc.geschenken g WHERE g.isBeschikbaar = true")
    List<GeschenkCategorie> findAllWithAvailableGeschenken();
}
