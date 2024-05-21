package com.yosto.yostobackend.studierichting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/studierichting")
public class StudierichtingController {

    private final StudierichtingService studierichtingService;

    public StudierichtingController(StudierichtingService studierichtingService) {
        this.studierichtingService = studierichtingService;
    }

    @GetMapping()
    public List<Studierichting> findAll() {
        return studierichtingService.findAll();
    }
}
