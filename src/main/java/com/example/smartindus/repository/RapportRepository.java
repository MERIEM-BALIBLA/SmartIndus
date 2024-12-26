package com.example.smartindus.repository;

import com.example.smartindus.domain.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RapportRepository extends JpaRepository<Rapport, UUID> {
}
