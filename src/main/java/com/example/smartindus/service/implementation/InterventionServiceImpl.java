package com.example.smartindus.service.implementation;


import com.example.smartindus.domain.Intervention;
import com.example.smartindus.repository.InterventionRepository;
import com.example.smartindus.service.interfaces.InterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public class InterventionServiceImpl implements InterventionService {

    @Autowired
    private InterventionRepository repository;

    @Override
    public Intervention save(Intervention intervention) {
        return repository.save(intervention);
    }

    @Override
    public Page<Intervention> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Intervention> findIntervention(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Intervention update(UUID id, Intervention intervention){
        Optional<Intervention> existingIntervention = findIntervention(id);
        if (existingIntervention.isPresent()) {
            Intervention updatedIntervention = existingIntervention.get();

            updatedIntervention.setDateDebut(intervention.getDateDebut());
            updatedIntervention.setDateFin(intervention.getDateFin());
            updatedIntervention.setType(intervention.getType());
            updatedIntervention.setStatut(intervention.getStatut());
            updatedIntervention.setEquipement(intervention.getEquipement());

            return repository.save(updatedIntervention);
        } else {
            throw new RuntimeException("Intervention avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public void delete(UUID id){
        Optional<Intervention> existingIntervention = findIntervention(id);
        if(existingIntervention.isPresent()){
            Intervention intervention = existingIntervention.get();
            repository.delete(intervention);
        }
    }
}
