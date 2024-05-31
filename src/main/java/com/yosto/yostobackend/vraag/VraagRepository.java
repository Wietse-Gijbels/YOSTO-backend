package com.yosto.yostobackend.vraag;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface VraagRepository extends JpaRepository<Vraag, UUID> {
}