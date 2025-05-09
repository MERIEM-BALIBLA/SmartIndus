package com.example.smartindus.repository;

import com.example.smartindus.domain.RapportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RapportRepository extends JpaRepository<RapportEntity, UUID> {
}
