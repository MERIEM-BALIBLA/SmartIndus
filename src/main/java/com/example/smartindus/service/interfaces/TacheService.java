package com.example.smartindus.service.interfaces;

import com.example.smartindus.DTO.Tache;
import com.example.smartindus.domain.TacheEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TacheService {


    Page<Tache> findAllTaches(Pageable pageable);

    Tache getTacheById(UUID id);

    @Transactional
    Tache createTache(Tache tache);

    @Transactional
    Tache updateTache(UUID id, Tache tache);

    @Transactional
    void deleteTache(UUID id);


    @Transactional
    Tache updateUserInTache(UUID tacheId, String userId);

    @Transactional
    Tache updateInterventionInTache(UUID tacheId, String interventionId);
}
