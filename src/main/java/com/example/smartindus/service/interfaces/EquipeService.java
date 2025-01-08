package com.example.smartindus.service.interfaces;

import com.example.smartindus.domain.Equipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface EquipeService {
    Equipe save(Equipe equipe);

    Page<Equipe> findAll(Pageable pageable);

    Optional<Equipe> findEquipe(UUID id);

    Equipe update(UUID id, Equipe equipe);

    void delete(UUID id);
}
