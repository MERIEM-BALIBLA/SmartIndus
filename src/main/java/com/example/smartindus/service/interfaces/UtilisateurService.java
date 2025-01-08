package com.example.smartindus.service.interfaces;

import com.example.smartindus.domain.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface UtilisateurService {
    Utilisateur save(Utilisateur utilisateur);

    Page<Utilisateur> findAll(Pageable pageable);

    Optional<Utilisateur> findUtilisateur(UUID id);

    Utilisateur update(UUID id, Utilisateur utilisateur);

    void delete(UUID id);
}
