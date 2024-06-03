package com.yosto.yostobackend.vraag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VraagService {

    @Value("${file.upload-dir-vragen}")
    private String fileUploadDir;

    @Autowired
    private VraagRepository vraagRepository;

    public List<Vraag> getAllVragen() {
        return vraagRepository.findAll();
    }

    public String getFileUploadDir() {
        return fileUploadDir;
    }
}
