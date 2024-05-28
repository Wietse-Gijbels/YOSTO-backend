package com.yosto.yostobackend.studierichting;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Page<Studierichting> findAll(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
        return studierichtingService.findAll(page, size);
    }
    @GetMapping("/dto")
    public List<String> findAllStudierichtingDTOs() {
        return studierichtingService.findAllStudierichtingDTOs();
    }

    @GetMapping("/hoger-onderwijs/dto")
    public List<String> findHogerOnderwijsRichtingenDTOs(@RequestParam(required = false) String filter) {
        return studierichtingService.findHogerOnderwijsRichtingenDTOs(filter);
    }

    @GetMapping("/middelbaar-onderwijs/dto")
    public List<String> findMiddelbaarOnderwijsRichtingenDTOs(@RequestParam(required = false) String filter) {
        return studierichtingService.findMiddelbaarOnderwijsRichtingenDTOs(filter);
    }

    @GetMapping("/all/dto")
    public List<String> findAllRichtingenDTOs(@RequestParam(required = false) String filter) {
        return studierichtingService.findAllRichtingenDTOs(filter);
    }
}
