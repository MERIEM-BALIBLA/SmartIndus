package com.example.smartindus.service.implementation;

import com.example.smartindus.domain.Tache;
import com.example.smartindus.repository.TacheRepository;
import com.example.smartindus.service.interfaces.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public class TacheServiceImpl implements TacheService {

    @Autowired
    private TacheRepository repository;

    @Override
    public Tache save(Tache tache) {
        return repository.save(tache);
    }

    @Override
    public Page<Tache> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Tache> findTache(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Tache update(UUID id, Tache tache){
        Optional<Tache> existingTache = findTache(id);
        if (existingTache.isPresent()) {
            Tache updatedTache = existingTache.get();

            updatedTache.setDepart(tache.getDepart());
            updatedTache.setFin(tache.getFin());
            updatedTache.setTitre(tache.getTitre());
            updatedTache.setDescription(tache.getDescription());
            updatedTache.setUtilisateur(tache.getUtilisateur());
            updatedTache.setIntervention(tache.getIntervention());
            updatedTache.setStatus(tache.getStatus());

            return repository.save(updatedTache);
        } else {
            throw new RuntimeException("Tache avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public void delete(UUID id){
        Optional<Tache> existingTache = findTache(id);
        if(existingTache.isPresent()){
            Tache tache = existingTache.get();
            repository.delete(tache);
        }
    }
}
