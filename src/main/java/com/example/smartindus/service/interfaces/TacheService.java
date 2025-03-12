package com.example.smartindus.service.interfaces;

import com.example.smartindus.domain.TacheEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

public interface TacheService {
    TacheEntity save(TacheEntity tacheEntity);

    Page<TacheEntity> findAllTaches(Pageable pageable);

    Optional<TacheEntity> findTache(UUID id);

    TacheEntity update(UUID id, TacheEntity tacheEntity);

    void delete(UUID id);
}
