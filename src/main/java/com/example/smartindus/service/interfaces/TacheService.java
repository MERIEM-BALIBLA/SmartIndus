package com.example.smartindus.service.interfaces;

import com.example.smartindus.domain.Tache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface TacheService {
    Tache save(Tache tache);

    Page<Tache> findAllTaches(Pageable pageable);

    Optional<Tache> findTache(UUID id);

    Tache update(UUID id, Tache tache);

    void delete(UUID id);
}
