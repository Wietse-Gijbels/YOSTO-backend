package com.yosto.yostobackend.studierichting;

import com.yosto.yostobackend.generic.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudierichtingService {

    private final StudierichtingRepository studierichtingRepository;

    public StudierichtingService(StudierichtingRepository studierichtingRepository) {
        this.studierichtingRepository = studierichtingRepository;
    }

    public Page<Studierichting> findAll(int page, int size) {
        return studierichtingRepository.findAll(PageRequest.of(page, size));
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
}
