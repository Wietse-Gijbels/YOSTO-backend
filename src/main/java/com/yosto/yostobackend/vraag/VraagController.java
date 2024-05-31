package com.yosto.yostobackend.vraag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vragen")
public class VraagController {

    @Autowired
    private VraagService vraagService;

    @GetMapping
    public List<Vraag> getAllVragen() {
        return vraagService.getAllVragen();
    }
}
