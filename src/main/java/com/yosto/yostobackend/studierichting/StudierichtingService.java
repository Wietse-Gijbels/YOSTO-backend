package com.yosto.yostobackend.studierichting;

import com.yosto.yostobackend.generic.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.UUID;

@Service
public class StudierichtingService {

    private final StudierichtingRepository studierichtingRepository;

    public StudierichtingService(StudierichtingRepository studierichtingRepository) {
        this.studierichtingRepository = studierichtingRepository;
    }

    public Page<Studierichting> findAll(int page, int size) {
        return studierichtingRepository.findAll(PageRequest.of(page, size));
    }

    public Studierichting findStudierichting(UUID id) {
        return studierichtingRepository.findById(id);
    }

    public Studierichting findByNaamAndNiveauNaam(String naam, String niveauNaam) {
        Optional<Studierichting> studierichting = studierichtingRepository.findByNaamAndNiveauNaam(naam, niveauNaam);
        if (studierichting.isPresent()) {
            return studierichting.get();
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("errorFindByNaam", "Er bestaat geen studierichting met deze naam en niveau.");
            throw new ServiceException(errors);
        }
    }
    public List<String> findAllStudierichtingDTOs() {
        List<Studierichting> studierichtingen = (List<Studierichting>) studierichtingRepository.findAll();
        return studierichtingen.stream()
                .map(s -> new StudierichtingDTO(s.getNaam(), s.getNiveauNaam()).toString())
                .collect(Collectors.toList());
    }

    public List<String> findHogerOnderwijsRichtingenDTOs(String filter) {
        List<Studierichting> studierichtingen;
        if (filter == null || filter.isEmpty() || filter.isBlank()) {
            studierichtingen = studierichtingRepository.findHogerOnderwijsRichtingen();
        } else {
            studierichtingen = studierichtingRepository.findHogerOnderwijsRichtingenWithFilter(filter);
        }
        return studierichtingen.stream()
                .map(s -> new StudierichtingDTO(s.getNaam(), s.getNiveauNaam()).toString())
                .collect(Collectors.toList());
    }

    public List<String> findMiddelbaarOnderwijsRichtingenDTOs(String filter) {
        List<Studierichting> studierichtingen;
        if (filter == null || filter.isEmpty() || filter.isBlank()) {
            studierichtingen = studierichtingRepository.findMiddelbaarOnderwijsRichtingen();
        } else {
            studierichtingen = studierichtingRepository.findMiddelbaarOnderwijsRichtingenWithFilter(filter);
        }
        return studierichtingen.stream()
                .map(s -> new StudierichtingDTO(s.getNaam(), s.getNiveauNaam()).toString())
                .collect(Collectors.toList());
    }

    public List<String> findAllRichtingenDTOs(String filter) {
        List<Studierichting> studierichtingen = studierichtingRepository.findAllRichtingenWithFilter(filter);
        return studierichtingen.stream()
                .map(s -> new StudierichtingDTO(s.getNaam(), s.getNiveauNaam()).toString())
                .collect(Collectors.toList());
    }

    public Page<StudierichtingDTO> findHogerOnderwijsRichtingenByNaamAndNiveauNaam(String naam, String niveauNaam, int page, int size) {
        List<Studierichting> studierichtingen;
        try {
            studierichtingen = studierichtingRepository.findHogerOnderwijsRichtingenWithOptionalFilters(naam, niveauNaam);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("errorFind", "Er is een fout opgetreden bij het zoeken naar studierichtingen.");
            throw new ServiceException(errors);
        }
        if (studierichtingen.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            errors.put("errorNoResults", "Geen studierichtingen gevonden met de gegeven filters.");
            throw new ServiceException(errors);
        }

        int start = (int) PageRequest.of(page, size).getOffset();
        int end = Math.min((start + PageRequest.of(page, size).getPageSize()), studierichtingen.size());

        List<StudierichtingDTO> dtoList = studierichtingen.subList(start, end).stream()
                .map(s -> new StudierichtingDTO(s.getNaam(), s.getNiveauNaam()))
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, PageRequest.of(page, size), studierichtingen.size());
    }
}
