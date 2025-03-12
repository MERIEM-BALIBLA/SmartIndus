package com.example.smartindus.service.implementation;

import com.example.smartindus.DTO.Tache;
import com.example.smartindus.DTO.TacheMapper;
import com.example.smartindus.domain.TacheEntity;
import com.example.smartindus.repository.TacheRepository;
import com.example.smartindus.service.interfaces.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TacheServiceImpl implements TacheService {

    @Autowired
    private TacheRepository repository;
    @Autowired
    private TacheMapper mapper;

    @Override
    public TacheEntity save(TacheEntity tacheEntity) {
        return repository.save(tacheEntity);
    }

    @Override
    public Page<Tache> findAllTaches(Pageable pageable){
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public Optional<TacheEntity> findTache(UUID id) {
        return repository.findById(id);
    }

    @Override
    public TacheEntity update(UUID id, TacheEntity tacheEntity){
        Optional<TacheEntity> existingTache = findTache(id);
        if (existingTache.isPresent()) {
            TacheEntity updatedTacheEntity = existingTache.get();

            updatedTacheEntity.setDepart(tacheEntity.getDepart());
            updatedTacheEntity.setFin(tacheEntity.getFin());
            updatedTacheEntity.setTitre(tacheEntity.getTitre());
            updatedTacheEntity.setDescription(tacheEntity.getDescription());
            updatedTacheEntity.setUserEntity(tacheEntity.getUserEntity());
            updatedTacheEntity.setInterventionEntity(tacheEntity.getInterventionEntity());
            updatedTacheEntity.setStatus(tacheEntity.getStatus());

            return repository.save(updatedTacheEntity);
        } else {
            throw new RuntimeException("Tache avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public void delete(UUID id){
        Optional<TacheEntity> existingTache = findTache(id);
        if(existingTache.isPresent()){
            TacheEntity tacheEntity = existingTache.get();
            repository.delete(tacheEntity);
        }
    }
}
