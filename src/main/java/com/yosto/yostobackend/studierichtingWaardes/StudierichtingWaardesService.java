package com.yosto.yostobackend.studierichtingWaardes;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudierichtingWaardesService {

    private final StudierichtingWaardesRepository repository;

    public StudierichtingWaardesService(StudierichtingWaardesRepository repository) {
        this.repository = repository;
    }

    public StudierichtingWaardes save(StudierichtingWaardes waardes) {
        return repository.save(waardes);
    }

    public List<StudierichtingWaardes> findAll() {
        return repository.findAll();
    }

    public StudierichtingWaardes findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
