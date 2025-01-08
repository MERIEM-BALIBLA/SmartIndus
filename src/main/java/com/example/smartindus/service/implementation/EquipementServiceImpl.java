package com.example.smartindus.service.implementation;

import com.example.smartindus.domain.Equipement;
import com.example.smartindus.repository.EquipementRepository;
import com.example.smartindus.service.interfaces.EquipementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public class EquipementServiceImpl implements EquipementService {
    @Autowired
    private EquipementRepository repository;

    @Override
    public Equipement save(Equipement equipement) {
        return repository.save(equipement);
    }

    @Override
    public Page<Equipement> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Equipement> findEquipement(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Equipement update(UUID id, Equipement equipement){
        Optional<Equipement> existingEquipement = findEquipement(id);
        if (existingEquipement.isPresent()) {
            Equipement updatedEquipement = existingEquipement.get();

            updatedEquipement.setEtatEquipement(equipement.getEtatEquipement());
            updatedEquipement.setNom(equipement.getNom());
            updatedEquipement.setType(equipement.getType());
            updatedEquipement.setDateInstallation(equipement.getDateInstallation());

            return repository.save(updatedEquipement);
        } else {
            throw new RuntimeException("Equipement avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public void delete(UUID id){
        Optional<Equipement> existingEquipement = findEquipement(id);
        if(existingEquipement.isPresent()){
            Equipement equipement = existingEquipement.get();
            repository.delete(equipement);
        }
    }
}
