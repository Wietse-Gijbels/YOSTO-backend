package com.yosto.yostobackend.vraag;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VraagService {

    @Value("${file.upload-dir-vragen}")
    private String fileUploadDir;

    private final VraagRepository vraagRepository;

    public VraagService(VraagRepository vraagRepository) {
        this.vraagRepository = vraagRepository;
    }

    public List<Vraag> getAllVragen() {
        return vraagRepository.findAll();
    }

    public String getFileUploadDir() {
        return fileUploadDir;
    }
}
