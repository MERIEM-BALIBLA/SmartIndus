package com.example.smartindus.service.implementation;

import com.example.smartindus.domain.Equipe;
import com.example.smartindus.repository.EquipeRepository;
import com.example.smartindus.service.interfaces.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public class EquipeServiceImpl implements EquipeService {
    @Autowired
    private EquipeRepository repository;

    @Override
    public Equipe save(Equipe equipe) {
        return repository.save(equipe);
    }

    @Override
    public Page<Equipe> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Equipe> findEquipe(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Equipe update(UUID id, Equipe equipe){
        Optional<Equipe> existingEquipe = findEquipe(id);
        if (existingEquipe.isPresent()) {
            Equipe updatedEquipe = existingEquipe.get();

            updatedEquipe.setOperateur(equipe.getOperateur());
            updatedEquipe.setResponsable(equipe.getResponsable());
            updatedEquipe.setTechniciens(equipe.getTechniciens());

            return repository.save(updatedEquipe);
        } else {
            throw new RuntimeException("Equipe avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public void delete(UUID id){
        Optional<Equipe> existingEquipe = findEquipe(id);
        if(existingEquipe.isPresent()){
            Equipe equipe = existingEquipe.get();
            repository.delete(equipe);
        }
    }
}
