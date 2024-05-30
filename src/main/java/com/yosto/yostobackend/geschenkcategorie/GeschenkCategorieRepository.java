package com.yosto.yostobackend.geschenkcategorie;

import com.yosto.yostobackend.geschenk.Geschenk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GeschenkCategorieRepository extends JpaRepository<GeschenkCategorie, UUID> {

    @Query("SELECT DISTINCT gc FROM GeschenkCategorie gc JOIN gc.geschenken g WHERE g.isBeschikbaar = true")
    List<GeschenkCategorie> findAllWithAvailableGeschenken();

    @Query("SELECT g FROM Geschenk g JOIN g.geschenkCategorie gc WHERE gc.id = :categoryId AND g.isBeschikbaar = true")
    List<Geschenk> findAvailableGeschenkenByCategoryId(UUID categoryId);

    Optional<GeschenkCategorie> findByNaam(String naam);

}
