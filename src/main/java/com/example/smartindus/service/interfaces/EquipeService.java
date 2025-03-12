package com.example.smartindus.service.interfaces;

import com.example.smartindus.domain.EquipeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface EquipeService {
    EquipeEntity save(EquipeEntity equipeEntity);

    Page<EquipeEntity> findAll(Pageable pageable);

    Optional<EquipeEntity> findEquipe(UUID id);

    EquipeEntity update(UUID id, EquipeEntity equipeEntity);

    void delete(UUID id);
}
