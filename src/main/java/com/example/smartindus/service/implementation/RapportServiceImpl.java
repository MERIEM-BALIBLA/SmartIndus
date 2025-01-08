package com.example.smartindus.service.implementation;

import com.example.smartindus.domain.Rapport;
import com.example.smartindus.repository.RapportRepository;
import com.example.smartindus.service.interfaces.RapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public class RapportServiceImpl implements RapportService {

    @Autowired
    private RapportRepository repository;

    @Override
    public Rapport save(Rapport rapport) {
        return repository.save(rapport);
    }

    @Override
    public Page<Rapport> findAllRapports(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Rapport> findRapport(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Rapport update(UUID id, Rapport rapport){
        Optional<Rapport> existingRapport = findRapport(id);
        if (existingRapport.isPresent()) {
            Rapport updatedRapport = existingRapport.get();

            updatedRapport.setDateGeneration(rapport.getDateGeneration());
            updatedRapport.setDateIntervention(rapport.getDateIntervention());
            updatedRapport.setTauxResolution(rapport.getTauxResolution());

            return repository.save(updatedRapport);
        } else {
            throw new RuntimeException("Rapport avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public void delete(UUID id){
        Optional<Rapport> existingRapport = findRapport(id);
        if(existingRapport.isPresent()){
            Rapport rapport = existingRapport.get();
            repository.delete(rapport);
        }
    }
}
