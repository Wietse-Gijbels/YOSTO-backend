package com.yosto.yostobackend.studierichting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
}
