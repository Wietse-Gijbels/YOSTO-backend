package com.yosto.yostobackend.geschenk;

import org.springframework.stereotype.Service;

@Service
public class GeschenkService {

    private final GeschenkRepository geschenkRepository;

    public GeschenkService(GeschenkRepository geschenkRepository) {
        this.geschenkRepository = geschenkRepository;
    }
}
