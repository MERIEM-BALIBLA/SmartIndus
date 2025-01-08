package com.example.smartindus.service.interfaces;

import com.example.smartindus.domain.Equipement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface EquipementService {
    Equipement save(Equipement equipement);

    Page<Equipement> findAll(Pageable pageable);

    Optional<Equipement> findEquipement(UUID id);

    Equipement update(UUID id, Equipement equipement);

    void delete(UUID id);
}
