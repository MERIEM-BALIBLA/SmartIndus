package com.example.smartindus.repository;

import com.example.smartindus.domain.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EquipeRepository extends JpaRepository<Equipe, UUID> {
}
