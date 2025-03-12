package com.example.smartindus.service.implementation;

import com.example.smartindus.domain.RapportEntity;
import com.example.smartindus.repository.RapportRepository;
import com.example.smartindus.service.interfaces.RapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RapportServiceImpl implements RapportService {

    @Autowired
    private RapportRepository repository;

    @Override
    public RapportEntity save(RapportEntity rapportEntity) {
        return repository.save(rapportEntity);
    }

    @Override
    public Page<RapportEntity> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Override
    public Optional<RapportEntity> findRapport(UUID id) {
        return repository.findById(id);
    }

    @Override
    public RapportEntity update(UUID id, RapportEntity rapportEntity){
        Optional<RapportEntity> existingRapport = findRapport(id);
        if (existingRapport.isPresent()) {
            RapportEntity updatedRapportEntity = existingRapport.get();

            updatedRapportEntity.setDateGeneration(rapportEntity.getDateGeneration());
            updatedRapportEntity.setDateIntervention(rapportEntity.getDateIntervention());
            updatedRapportEntity.setTauxResolution(rapportEntity.getTauxResolution());

            return repository.save(updatedRapportEntity);
        } else {
            throw new RuntimeException("Rapport avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public void delete(UUID id){
        Optional<RapportEntity> existingRapport = findRapport(id);
        if(existingRapport.isPresent()){
            RapportEntity rapportEntity = existingRapport.get();
            repository.delete(rapportEntity);
        }
    }
}
