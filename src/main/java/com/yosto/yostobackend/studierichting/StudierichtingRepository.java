package com.yosto.yostobackend.studierichting;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudierichtingRepository extends PagingAndSortingRepository<Studierichting, UUID> {
    Optional<Studierichting> findByNaamAndNiveauNaam(String naam, String niveauNaam);

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
}
