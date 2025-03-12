package com.example.smartindus.repository;

import com.example.smartindus.domain.EquipementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EquipementRepository extends JpaRepository<EquipementEntity, UUID> {

    boolean existsByNom(String nom);
}
