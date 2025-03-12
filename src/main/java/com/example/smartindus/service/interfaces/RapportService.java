package com.example.smartindus.service.interfaces;

import com.example.smartindus.domain.RapportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface RapportService {
    RapportEntity save(RapportEntity rapportEntity);

    Page<RapportEntity> findAll(Pageable pageable);

    Optional<RapportEntity> findRapport(UUID id);

    RapportEntity update(UUID id, RapportEntity rapportEntity);

    void delete(UUID id);
}
