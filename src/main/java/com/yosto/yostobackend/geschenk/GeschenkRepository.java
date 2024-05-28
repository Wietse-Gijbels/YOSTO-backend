package com.yosto.yostobackend.geschenk;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GeschenkRepository extends JpaRepository<Geschenk, UUID> {
}
