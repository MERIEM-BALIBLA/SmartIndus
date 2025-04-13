package com.example.smartindus.service.interfaces;

import com.example.smartindus.DTO.Equipe;
import com.example.smartindus.domain.EquipeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EquipeService {

    List<Equipe> getAllEquipes();

    Equipe getEquipeById(UUID id);

    @Transactional
    Equipe createEquipe(Equipe equipeDto);

    @Transactional
    Equipe updateEquipe(UUID id, Equipe equipeDto);

    @Transactional
    void deleteEquipe(UUID id);
}
