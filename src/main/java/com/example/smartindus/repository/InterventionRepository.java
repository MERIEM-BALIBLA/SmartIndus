package com.example.smartindus.repository;

import com.example.smartindus.domain.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InterventionRepository extends JpaRepository<Intervention, UUID> {
}
