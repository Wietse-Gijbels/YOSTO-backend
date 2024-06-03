package com.yosto.yostobackend.studierichting;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/studierichting")
public class StudierichtingController {

    private final StudierichtingService studierichtingService;

    public StudierichtingController(StudierichtingService studierichtingService) {
        this.studierichtingService = studierichtingService;
    }

    @GetMapping("/all/{page}")
    public Page<Studierichting> findAll(@PathVariable() int page) {
        return studierichtingService.findAll(page, 10);
    }

    @GetMapping("/{id}")
    public Studierichting findStudierichtingById(@PathVariable() UUID id) {
        return studierichtingService.findStudierichtingById(id);
    }
}
