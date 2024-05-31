package com.yosto.yostobackend.vraag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VraagService {

    @Autowired
    private VraagRepository vraagRepository;

    public List<Vraag> getAllVragen() {
        return vraagRepository.findAll();
    }
}
