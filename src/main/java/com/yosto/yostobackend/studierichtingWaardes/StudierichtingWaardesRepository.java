package com.yosto.yostobackend.studierichtingWaardes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudierichtingWaardesRepository extends JpaRepository<StudierichtingWaardes, UUID>{
    StudierichtingWaardes findByStudierichtingId(UUID studierichtingId);
}
