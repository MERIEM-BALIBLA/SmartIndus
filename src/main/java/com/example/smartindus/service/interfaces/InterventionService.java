package com.example.smartindus.service.interfaces;

import com.example.smartindus.domain.Intervention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface InterventionService {

    Intervention save(Intervention intervention);

    Page<Intervention> findAll(Pageable pageable);

    Optional<Intervention> findIntervention(UUID id);

    Intervention update(UUID id, Intervention intervention);

    void delete(UUID id);
}
