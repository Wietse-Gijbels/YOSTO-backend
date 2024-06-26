package com.yosto.yostobackend.studierichting;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudierichtingRepository extends PagingAndSortingRepository<Studierichting, UUID> {
    Optional<Studierichting> findByNaamAndNiveauNaam(String naam, String niveauNaam);

    Studierichting findById(UUID id);

    List<Studierichting> findAll();

    @Query("SELECT s FROM Studierichting s WHERE s.niveauNaam IN ('Academische bachelor', 'Master', 'Bachelor-na-bachelor', 'Graduaatsopleiding', 'Professionele bachelor')")
    List<Studierichting> findHogerOnderwijsRichtingen();

    @Query("SELECT s FROM Studierichting s WHERE s.niveauNaam IN ('Buitengewoon voltijds secundair onderwijs', 'Gewoon voltijds secundair onderwijs', 'Gewoon deeltijds beroepssecundair onderwijs')")
    List<Studierichting> findMiddelbaarOnderwijsRichtingen();

    @Query("SELECT s FROM Studierichting s WHERE s.niveauNaam IN ('Academische bachelor', 'Master', 'Bachelor-na-bachelor', 'Graduaatsopleiding', 'Professionele bachelor') AND LOWER(s.naam) LIKE LOWER(CONCAT('%', :filter, '%'))")
    List<Studierichting> findHogerOnderwijsRichtingenWithFilter(String filter);

    @Query("SELECT s FROM Studierichting s WHERE s.niveauNaam IN ('Buitengewoon voltijds secundair onderwijs', 'Gewoon voltijds secundair onderwijs', 'Gewoon deeltijds beroepssecundair onderwijs') AND LOWER(s.naam) LIKE LOWER(CONCAT('%', :filter, '%'))")
    List<Studierichting> findMiddelbaarOnderwijsRichtingenWithFilter(String filter);

    @Query("SELECT s FROM Studierichting s WHERE LOWER(s.naam) LIKE LOWER(CONCAT('%', :filter, '%'))")
    List<Studierichting> findAllRichtingenWithFilter(String filter);

    @Query("SELECT DISTINCT s FROM Studierichting s LEFT JOIN FETCH s.afstudeerrichtingen a LEFT JOIN FETCH a.instellingen i WHERE " +
            "(:naam IS NULL OR :naam = '' OR LOWER(s.naam) LIKE LOWER(CONCAT('%', :naam, '%'))) AND " +
            "(:niveauNaam IS NULL OR :niveauNaam = '' OR LOWER(s.niveauNaam) LIKE LOWER(CONCAT('%', :niveauNaam, '%'))) " +
            "AND s.niveauNaam IN ('Academische bachelor', 'Master', 'Bachelor-na-bachelor', 'Graduaatsopleiding', 'Professionele bachelor')")
    List<Studierichting> findHogerOnderwijsRichtingenWithOptionalFilters(@Param("naam") String naam, @Param("niveauNaam") String niveauNaam);


}
