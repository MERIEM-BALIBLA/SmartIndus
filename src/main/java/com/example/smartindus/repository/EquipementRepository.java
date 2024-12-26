package com.example.smartindus.repository;

import com.example.smartindus.domain.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EquipementRepository extends JpaRepository<Equipement, UUID> {
}
