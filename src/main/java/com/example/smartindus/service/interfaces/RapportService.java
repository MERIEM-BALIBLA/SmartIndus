package com.example.smartindus.service.interfaces;

import com.example.smartindus.domain.Rapport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface RapportService {
    Rapport save(Rapport rapport);

    Page<Rapport> findAllRapports(Pageable pageable);

    Optional<Rapport> findRapport(UUID id);

    Rapport update(UUID id, Rapport rapport);

    void delete(UUID id);
}
