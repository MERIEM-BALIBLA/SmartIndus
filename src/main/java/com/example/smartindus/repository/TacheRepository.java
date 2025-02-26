package com.example.smartindus.repository;

import com.example.smartindus.domain.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TacheRepository extends JpaRepository<Tache, UUID> {
}
