package com.example.smartindus.service.implementation;

import com.example.smartindus.domain.EquipeEntity;
import com.example.smartindus.repository.EquipeRepository;
import com.example.smartindus.service.interfaces.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EquipeServiceImpl implements EquipeService {
    @Autowired
    private EquipeRepository repository;

    @Override
    public EquipeEntity save(EquipeEntity equipeEntity) {
        return repository.save(equipeEntity);
    }

    @Override
    public Page<EquipeEntity> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Override
    public Optional<EquipeEntity> findEquipe(UUID id) {
        return repository.findById(id);
    }

    @Override
    public EquipeEntity update(UUID id, EquipeEntity equipeEntity){
        Optional<EquipeEntity> existingEquipe = findEquipe(id);
        if (existingEquipe.isPresent()) {
            EquipeEntity updatedEquipeEntity = existingEquipe.get();

            updatedEquipeEntity.setOperateur(equipeEntity.getOperateur());
            updatedEquipeEntity.setResponsable(equipeEntity.getResponsable());
            updatedEquipeEntity.setTechniciens(equipeEntity.getTechniciens());

            return repository.save(updatedEquipeEntity);
        } else {
            throw new RuntimeException("Equipe avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public void delete(UUID id){
        Optional<EquipeEntity> existingEquipe = findEquipe(id);
        if(existingEquipe.isPresent()){
            EquipeEntity equipeEntity = existingEquipe.get();
            repository.delete(equipeEntity);
        }
    }
}
