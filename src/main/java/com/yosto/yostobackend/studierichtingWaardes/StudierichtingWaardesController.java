package com.yosto.yostobackend.studierichtingWaardes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/studierichtingWaardes")
public class StudierichtingWaardesController {

    private final StudierichtingWaardesService service;

    public StudierichtingWaardesController(StudierichtingWaardesService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StudierichtingWaardes> create(@RequestBody StudierichtingWaardes waardes) {
        return new ResponseEntity<>(service.save(waardes), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StudierichtingWaardes>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudierichtingWaardes> getById(@PathVariable UUID id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}