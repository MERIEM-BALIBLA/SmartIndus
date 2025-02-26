package com.example.smartindus.repository;

import com.example.smartindus.domain.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EquipementRepository extends JpaRepository<Equipement, UUID> {
}
