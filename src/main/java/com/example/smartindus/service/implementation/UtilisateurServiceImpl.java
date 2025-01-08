package com.example.smartindus.service.implementation;

import com.example.smartindus.domain.Utilisateur;
import com.example.smartindus.repository.UtilisateurRepository;
import com.example.smartindus.service.interfaces.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository repository;

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        return repository.save(utilisateur);
    }

    @Override
    public Page<Utilisateur> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Utilisateur> findUtilisateur(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Utilisateur update(UUID id, Utilisateur utilisateur){
        Optional<Utilisateur> existingUtilisateur = findUtilisateur(id);
        if (existingUtilisateur.isPresent()) {
            Utilisateur updatedUtilisateur = existingUtilisateur.get();

            updatedUtilisateur.setNom(utilisateur.getNom());
            updatedUtilisateur.setEmail(utilisateur.getEmail());
            updatedUtilisateur.setPrenom(utilisateur.getPrenom());
            updatedUtilisateur.setCin(utilisateur.getCin());
            updatedUtilisateur.setRole(utilisateur.getRole());
            updatedUtilisateur.setTelephone(utilisateur.getTelephone());

            return repository.save(updatedUtilisateur);
        } else {
            throw new RuntimeException("Utilisateur avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public void delete(UUID id){
        Optional<Utilisateur> existingUtilisateur = findUtilisateur(id);
        if(existingUtilisateur.isPresent()){
            Utilisateur utilisateur = existingUtilisateur.get();
            repository.delete(utilisateur);
        }
    }
}
