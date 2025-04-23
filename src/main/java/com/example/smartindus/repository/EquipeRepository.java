package com.example.smartindus.repository;

import com.example.smartindus.domain.EquipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EquipeRepository extends JpaRepository<EquipeEntity, UUID> {

}
