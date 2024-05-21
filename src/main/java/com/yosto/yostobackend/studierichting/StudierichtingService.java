package com.yosto.yostobackend.studierichting;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudierichtingService {

    private final StudierichtingRepository studierichtingRepository;

    public StudierichtingService(StudierichtingRepository studierichtingRepository) {
        this.studierichtingRepository = studierichtingRepository;
    }


    public List<Studierichting> findAll() {
        return this.studierichtingRepository.findAll();
    }
}
